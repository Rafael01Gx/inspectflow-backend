package br.com.inspectflow.application.stock.dto;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record UpdateStockItemRequest(

        @Size(min = 3, max = 50)
        String name,

        @Size(min = 3, max = 50)
        String type,

        String partCategory,

        @PositiveOrZero
        Integer quantity,

        String supplierCode,

        List<String> linkedEquipmentIds,

        @Size(min = 3, max = 50)
        String location,

        @PositiveOrZero
        Integer minQuantity
) {
}
