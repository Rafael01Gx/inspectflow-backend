package br.com.inspectflow.application.stock.dto;

import br.com.inspectflow.domain.stock.enums.PartCategory;
import br.com.inspectflow.domain.stock.enums.StockType;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateStockItemRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 50)
        String name,

        @NotNull(message = "O tipo é obrigatório")
        StockType type,

        @NotNull(message = "A categoria é obrigatória")
        PartCategory partCategory,

        @PositiveOrZero(message = "A quantidade deve ser um número positivo ou zero")
        Integer quantity,

        @Size(max = 50, message = "O código do fornecedor deve ter entre 3 e 50 caracteres")
        String supplierCode,

        @JsonAlias("linkedEquipmentIds")
        List<String> linkedEquipments,

        @NotBlank(message = "A localização é obrigatória")
        @Size(max = 50, message = "A localização deve ter entre 3 e 50 caracteres")
        String location,

        @PositiveOrZero(message = "O valor deve ser um número positivo ou zero")
        Integer minQuantity
) {

}
