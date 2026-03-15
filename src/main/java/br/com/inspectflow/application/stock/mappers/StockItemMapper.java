package br.com.inspectflow.application.stock.mappers;

import br.com.inspectflow.application.stock.dto.CreateStockItemRequest;
import br.com.inspectflow.domain.stock.models.StockItem;

public class StockItemMapper {

    public static StockItem toStockItem(CreateStockItemRequest dto){
        return StockItem.builder()
                .name(dto.name())
                .type(dto.type())
                .partCategory(dto.partCategory())
                .quantity(dto.quantity())
                .supplierCode(dto.supplierCode())
                .location(dto.location())
                .minQuantity(dto.minQuantity())
                .build();
    }
}
