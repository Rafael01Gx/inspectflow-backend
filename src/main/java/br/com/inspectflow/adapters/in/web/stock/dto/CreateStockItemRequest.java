package br.com.inspectflow.adapters.in.web.stock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateStockItemRequest(
        @NotBlank
        @Size(min = 3, max = 50)
        String name,

        @NotBlank
        @Size(min = 3, max = 50)
        String type,

        @NotBlank
        String partCategory,

        @PositiveOrZero
        Integer quantity,

        @NotBlank
        String supplierCode,

        List<String> linkedEquipmentIds,

        @NotBlank
        @Size(min = 3, max = 50)
        String location,

        @PositiveOrZero
        Integer minQuantity
) {
}
