package br.com.inspectflow.application.equipment.dto;

import br.com.inspectflow.application.Inspection.dto.UpdateInspectionItemRequest;
import br.com.inspectflow.domain.equipment.enums.EquipmentComponentCategory;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record UpdateEquipmentComponentRequest(
        UUID id,

        @NotBlank(message = "Nome do componente é obrigatório")
        String name,

        @NotNull(message = "Categoria do componente é obrigatória")
        EquipmentComponentCategory category,

        @NotNull(message = "Lista de itens de inspeção é obrigatória")
        @JsonAlias("inspectionItem")
        @Valid
        Set<UpdateInspectionItemRequest> items
) {
}
