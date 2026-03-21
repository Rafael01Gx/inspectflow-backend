package br.com.inspectflow.application.stock.dto;

import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.stock.models.StockItem;
import lombok.Builder;

import java.util.Collections;
import java.util.List;

@Builder
public record StockItemResponse(
        Long id,
        String name,
        String type,
        String partCategory,
        Integer quantity,
        String supplierCode,
        List<String> linkedEquipmentCodes,
        String location,
        Integer minQuantity
) {


    public static StockItemResponse from(StockItem stockItem) {
        return StockItemResponse.builder()
                .id(stockItem.getId())
                .name(stockItem.getName())
                .type(stockItem.getType().name())
                .partCategory(stockItem.getPartCategory().name())
                .quantity(stockItem.getQuantity())
                .supplierCode(stockItem.getSupplierCode())
                .linkedEquipmentCodes(stockItem.getLinkedEquipments() != null ?
                        stockItem.getLinkedEquipments().stream()
                                .map(Equipment::getCode)
                                .toList() : Collections.emptyList())
                .location(stockItem.getLocation())
                .minQuantity(stockItem.getMinQuantity())
                .build();
    }

}
