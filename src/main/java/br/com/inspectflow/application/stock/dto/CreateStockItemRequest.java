package br.com.inspectflow.application.stock.dto;

import br.com.inspectflow.domain.stock.enums.PartCategory;
import br.com.inspectflow.domain.stock.enums.StockType;
import br.com.inspectflow.domain.stock.models.StockItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record CreateStockItemRequest(
        @NotBlank
        @Size(min = 3, max = 50)
        String name,

        @NotBlank
        @Size(min = 3, max = 50)
        StockType type,

        @NotBlank
        PartCategory partCategory,

        @PositiveOrZero
        Integer quantity,

        @Size(min = 3, max = 50)
        String supplierCode,

        List<UUID> linkedEquipmentIds,

        @NotBlank
        @Size(min = 3, max = 50)
        String location,

        @PositiveOrZero
        Integer minQuantity
) {

}
