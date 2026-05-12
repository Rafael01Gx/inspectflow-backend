package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.bucket.services.UploadFileService;
import br.com.inspectflow.application.equipment.services.FindManyEquipmentsByCodeService;
import br.com.inspectflow.application.equipment.validators.AttachmentFileIsValid;
import br.com.inspectflow.application.stock.dto.CreateStockItemRequest;
import br.com.inspectflow.application.stock.dto.StockItemResponse;
import br.com.inspectflow.application.stock.mappers.StockItemMapper;
import br.com.inspectflow.application.stock.ports.in.CreateStockItemsUseCase;
import br.com.inspectflow.application.stock.validators.ValidateStockItemDoesNotExist;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.stock.models.StockItem;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateStockItemService implements CreateStockItemsUseCase {

    private final StockItemRepository stockItemRepository;
    private final FindManyEquipmentsByCodeService findManyEquipmentsByCodeService;
    private final ValidateStockItemDoesNotExist validate;
    private final AttachmentFileIsValid fileValidator;
    private final UploadFileService uploadFileService;

    @Override
    @Transactional
    @Observed(name = "stock.create",
            contextualName = "cria item de estoque")
    public StockItemResponse execute(CreateStockItemRequest dto, MultipartFile file) {
        validate.execute(dto);
        StockItem stockItem = StockItemMapper.toStockItem(dto);

        var savedStockItem = stockItemRepository.save(stockItem);

        if(dto.linkedEquipmentCodes() != null){
            linkEquipmentsTo(stockItem, dto.linkedEquipmentCodes());
        }

        if (file != null && !file.isEmpty()) {
            fileValidator.execute(file);
            var imageUrl = uploadFileService.execute("stock-item",file);
            savedStockItem.setImageUrl(imageUrl);
        }

        return StockItemResponse.from(savedStockItem);
    }


    private void linkEquipmentsTo(StockItem stockItem, List<String> equipmentIds) {
        if (equipmentIds == null || equipmentIds.isEmpty()) return;
        List<Equipment> foundEquipments = findManyEquipmentsByCodeService.execute(equipmentIds);

        foundEquipments.forEach(stockItem::addEquipment);
    }
}
