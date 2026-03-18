package br.com.inspectflow.application.equipment.dto;

import br.com.inspectflow.application.equipment_component.dto.CreateEquipmentComponentRequest;
import br.com.inspectflow.domain.equipment.enums.EquipmentStatus;
import br.com.inspectflow.domain.equipment.enums.EquipmentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateEquipmentRequest(

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 255)
        String name,

        @NotBlank(message = "O código é obrigatório")
        @Size(min = 3, max = 50)
        String code,

        @NotNull(message = "O status do equipamento é obrigatório")
        EquipmentStatus status,

        @NotNull(message = "O tipo do equipamento é obrigatório")
        EquipmentType type,

        @NotBlank(message = "A localização é obrigatória")
        String location,

        @Valid
        Set<CreateEquipmentComponentRequest> components
) {
}
