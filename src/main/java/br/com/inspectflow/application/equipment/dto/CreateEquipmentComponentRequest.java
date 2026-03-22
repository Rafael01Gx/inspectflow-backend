package br.com.inspectflow.application.equipment.dto;

import br.com.inspectflow.application.Inspection.dto.CreateInspectionItemRequest;
import br.com.inspectflow.domain.common.enums.PartCategory;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public record CreateEquipmentComponentRequest(

        @NotBlank(message = "Nome do componente é obrigatório")
        String name,

        @NotNull(message = "Categoria do componente é obrigatória")
        PartCategory category,

        @NotNull(message = "Lista de itens de inspeção é obrigatória")
        @Valid
        @JsonAlias("inspectionItem")
        Set<CreateInspectionItemRequest> items
) {
}
