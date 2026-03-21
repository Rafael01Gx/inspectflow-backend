package br.com.inspectflow.application.Inspection.dto;

import br.com.inspectflow.domain.inspection.enums.InspectionItemStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InspectionItemResultRequest(
        @NotBlank(message = "Título é obrigatório")
        String title,

        @NotBlank(message = "Descrição é obrigatória")
        String description,

        @NotNull(message = "Status é obrigatório")
        InspectionItemStatus status,

        @NotNull(message = "Impedimento é obrigatório")
        boolean impedimentItem,

        String observation
) {
}
