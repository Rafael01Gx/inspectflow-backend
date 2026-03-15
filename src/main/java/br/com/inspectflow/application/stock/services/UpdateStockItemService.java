package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.common.validators.IdConsistencyValidator;
import br.com.inspectflow.application.equipment.services.FindManyEquipmentsService;
import br.com.inspectflow.application.http.handlers.StockItemNotFoundException;
import br.com.inspectflow.application.stock.dto.StockItemResponse;
import br.com.inspectflow.application.stock.dto.UpdateStockItemRequest;
import br.com.inspectflow.application.stock.ports.in.UpdateStockItemUseCase;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.stock.models.StockItem;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateStockItemService implements UpdateStockItemUseCase {

    private final StockItemRepository repository;
    private final IdConsistencyValidator<Long> idConsistencyValidator;
    private final FindManyEquipmentsService findManyEquipmentsService;

    @Override
    @Transactional
    public StockItemResponse execute(Long id, UpdateStockItemRequest dto) {
        idConsistencyValidator.execute(id,dto.id());

        StockItem item = repository.findById(id).orElseThrow(StockItemNotFoundException::new);
        item.update(dto);

        if(dto.linkedEquipmentIds() != null) {
            List<Equipment> equipments = findManyEquipmentsService.execute(dto.linkedEquipmentIds());
            item.addEquipments(equipments);

        }
        StockItem savedItem = repository.save(item);
        return StockItemResponse.from(savedItem);
    }
}
