package br.com.inspectflow.application.equipment_component.dto;

import br.com.inspectflow.application.Inspection_item.dto.UpdateInspectionItemRequest;
import br.com.inspectflow.domain.equipment_component.enums.EquipmentComponentCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record UpdateEquipmentComponentRequest(
        @NotNull
        UUID id,

        @NotBlank(message = "Nome do componente é obrigatório")
        String name,

        @NotNull(message = "Categoria do componente é obrigatória")
        EquipmentComponentCategory category,

        @NotNull(message = "Lista de itens de inspeção é obrigatória")
        @Valid
        Set<UpdateInspectionItemRequest> items
) {
}
