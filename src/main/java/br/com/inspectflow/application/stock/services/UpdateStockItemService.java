package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.bucket.services.DeleteFileService;
import br.com.inspectflow.application.bucket.services.UploadFileService;
import br.com.inspectflow.application.common.validators.IdConsistencyValidator;
import br.com.inspectflow.application.equipment.services.FindManyEquipmentsByCodeService;
import br.com.inspectflow.application.equipment.validators.AttachmentFileIsValid;
import br.com.inspectflow.application.http.handlers.StockItemNotFoundException;
import br.com.inspectflow.application.stock.dto.StockItemResponse;
import br.com.inspectflow.application.stock.dto.UpdateStockItemRequest;
import br.com.inspectflow.application.stock.ports.in.UpdateStockItemUseCase;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.stock.models.StockItem;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateStockItemService implements UpdateStockItemUseCase {

    private final StockItemRepository repository;
    private final IdConsistencyValidator<Long> idConsistencyValidator;
    private final FindManyEquipmentsByCodeService findManyEquipmentsByCodeService;
    private final AttachmentFileIsValid fileValidator;
    private final UploadFileService uploadFileService;
    private final DeleteFileService deleteFileService;

    @Override
    @Transactional
    @CacheEvict(value = "dashboardStockItems", key = "'lowQuantity'")
    @Observed(name = "stock.update",
            contextualName = "atualiza item de estoque")
    public StockItemResponse execute(Long id, UpdateStockItemRequest dto, MultipartFile file) {
        idConsistencyValidator.execute(id,dto.id());

        StockItem item = repository.findById(id).orElseThrow(StockItemNotFoundException::new);

        if (file != null && !file.isEmpty()) {
            fileValidator.execute(file);
            var imageUrl = uploadFileService.execute("stock-item",file);
            try {
                Optional.ofNullable(item.getImageUrl()).ifPresent(deleteFileService::deleteFile);
                item.setImageUrl(imageUrl);
            } catch (Exception e) {
                log.error("Erro ao deletar imagem antiga do MinIO");
                deleteFileService.deleteFile(imageUrl);
            }
        }

        item.update(dto);

        StockItem savedItem = repository.save(item);

        diffLink(dto,savedItem);


        return StockItemResponse.from(savedItem);
    }


    private void diffLink(UpdateStockItemRequest dto,StockItem item){
        if (dto.linkedEquipmentCodes() != null) {
            List<Equipment> newEquipments = findManyEquipmentsByCodeService.execute(dto.linkedEquipmentCodes());
            Set<Equipment> newEquipmentSet = new HashSet<>(newEquipments);
            Set<Equipment> currentEquipments = new HashSet<>(item.getLinkedEquipments());

            currentEquipments.forEach(equipment -> {
                if (!newEquipmentSet.contains(equipment)) {
                    equipment.getPartsInStock().remove(item);
                    item.getLinkedEquipments().remove(equipment);
                }
            });

            newEquipmentSet.forEach(equipment -> {
                if (!item.getLinkedEquipments().contains(equipment)) {
                    equipment.getPartsInStock().add(item);
                    item.getLinkedEquipments().add(equipment);
                }
            });

        } else {
            Set<Equipment> equipments = new HashSet<>(item.getLinkedEquipments());
            equipments.forEach(equipment -> equipment.getPartsInStock().remove(item));
            item.getLinkedEquipments().clear();
        }
    }
}
