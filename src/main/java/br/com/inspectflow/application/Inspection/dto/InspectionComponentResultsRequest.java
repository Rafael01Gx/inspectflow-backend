package br.com.inspectflow.application.Inspection.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InspectionComponentResultsRequest(
        @NotNull(message = "Id do componente é obrigatório")
        UUID componentId,

        @NotBlank(message = "Nome do componente é obrigatório")
        String componentName,

        @Valid
        List<InspectionItemResultRequest> items
) {
}
