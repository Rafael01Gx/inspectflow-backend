package br.com.inspectflow.application.equipment.dto;

import br.com.inspectflow.domain.equipment.enums.EquipmentComponentCategory;
import br.com.inspectflow.domain.equipment.enums.EquipmentStatus;
import br.com.inspectflow.domain.equipment.enums.EquipmentType;
import br.com.inspectflow.domain.equipment.enums.InspectionFrequency;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Map;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateEquipmentRequest(

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 255)
        String name,

        @NotBlank(message = "O código é obrigatório")
        @Size(min = 3, max = 50)
        @Pattern(regexp = "^[a-zA-Z]{1,5}-[0-9]{1,5}$",
                message = "O formato deve ser de 1-5 letras, um hífen e 1-5 números (ex: ABC-123)")
        String code,

        @NotNull(message = "O status do equipamento é obrigatório")
        EquipmentStatus status,

        @NotNull(message = "O tipo do equipamento é obrigatório")
        EquipmentType type,

        @NotBlank(message = "A localização é obrigatória")
        String location,

        Map<EquipmentComponentCategory, String> consignmentCodes ,

        @NotNull
        InspectionFrequency inspectionfrequency,

        @Valid
        Set<CreateEquipmentComponentRequest> components
) {
}
