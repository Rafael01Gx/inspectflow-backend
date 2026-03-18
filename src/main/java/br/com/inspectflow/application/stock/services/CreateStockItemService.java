package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.equipment.services.FindManyEquipmentsByCodeService;
import br.com.inspectflow.application.equipment.services.FindManyEquipmentsByIdByIdService;
import br.com.inspectflow.application.stock.dto.CreateStockItemRequest;
import br.com.inspectflow.application.stock.dto.StockItemResponse;
import br.com.inspectflow.application.stock.mappers.StockItemMapper;
import br.com.inspectflow.application.stock.ports.in.CreateStockItemsUseCase;
import br.com.inspectflow.application.stock.validators.ValidateStockItemDoesNotExist;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.stock.models.StockItem;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateStockItemService implements CreateStockItemsUseCase {

    private final StockItemRepository stockItemRepository;
    private final FindManyEquipmentsByCodeService findManyEquipmentsByCodeService;
    private final ValidateStockItemDoesNotExist validate;

    @Override
    @Transactional
    public StockItemResponse execute(CreateStockItemRequest dto) {
        validate.execute(dto);
        StockItem stockItem = StockItemMapper.toStockItem(dto);

        if(dto.linkedEquipments() != null){
            linkEquipmentsTo(stockItem, dto.linkedEquipments());
        }
        return StockItemResponse.from(stockItemRepository.save(stockItem));
    }


    private void linkEquipmentsTo(StockItem stockItem, List<String> equipmentIds) {
        if (equipmentIds == null || equipmentIds.isEmpty()) return;
        List<Equipment> foundEquipments = findManyEquipmentsByCodeService.execute(equipmentIds);
        foundEquipments.forEach(stockItem::addEquipament);
    }
}
