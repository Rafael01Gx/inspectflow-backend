package br.com.inspectflow.application.Inspection.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InspectionRequest(

        @NotNull(message = "Id do equipamento é obrigatório")
        UUID equipmentId,

        @NotNull(message = "Termo de responsabilidade é obrigatório")
        boolean responsibilityAccepted,

        LocalDateTime nextInspection,

        String notes,

        @Valid
        List<InspectionComponentResultsRequest> componentResults
) {
}
