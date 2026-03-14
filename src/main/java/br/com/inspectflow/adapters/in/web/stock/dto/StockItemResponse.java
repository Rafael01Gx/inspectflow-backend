package br.com.inspectflow.adapters.in.web.stock.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record StockItemResponse(

        Long id,
        String name,
        String type,
        String partCategory,
        Integer quantity,
        String supplierCode,
        List<String> linkedEquipmentIds,
        String location,
        Integer minQuantity
) {
}
