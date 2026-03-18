package br.com.inspectflow.application.equipment_component.dto;

import br.com.inspectflow.application.Inspection_item.dto.CreateInspectionItemRequest;
import br.com.inspectflow.domain.equipment_component.enums.EquipmentComponentCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateEquipmentComponentRequest(

        @NotBlank(message = "Nome do componente é obrigatório")
        String name,

        @NotNull(message = "Categoria do componente é obrigatória")
        EquipmentComponentCategory category,

        @NotNull(message = "Lista de itens de inspeção é obrigatória")
        @Valid
        Set<CreateInspectionItemRequest> items
) {
}
