package br.com.inspectflow.application.stock.dto;

import br.com.inspectflow.domain.common.enums.PartCategory;
import br.com.inspectflow.domain.stock.enums.StockType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateStockItemRequest(

        @NotNull(message = "O ID é obrigatório")
        Long id,

        @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
        String name,

        @NotNull(message = "O tipo é obrigatório")
        StockType type,

        @NotNull(message = "A categoria é obrigatória")
        PartCategory partCategory,

        @PositiveOrZero(message = "A quantidade deve ser um número positivo ou zero")
        Integer quantity,

        String supplierCode,

        List<String> linkedEquipmentCodes,

        @Size(min = 3, max = 50, message = "A localização deve ter entre 3 e 50 caracteres")
        String location,

        @PositiveOrZero(message = "O valor deve ser um número positivo ou zero")
        Integer minQuantity
) {
}
