package br.com.inspectflow.application.equipment.dto;

import br.com.inspectflow.application.equipment_component.dto.UpdateEquipmentComponentRequest;
import br.com.inspectflow.domain.equipment.enums.EquipmentStatus;
import br.com.inspectflow.domain.equipment.enums.EquipmentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.UUID;

public record UpdateEquipmentRequest(

        @NotNull(message = "O id é obrigatório")
        UUID id,

        @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres")
        String name,

        EquipmentStatus status,

        EquipmentType type,

        String location,

        @Valid
        Set<UpdateEquipmentComponentRequest> components
) {
}
