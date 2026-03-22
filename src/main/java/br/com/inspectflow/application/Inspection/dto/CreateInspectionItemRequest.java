package br.com.inspectflow.application.Inspection.dto;

import br.com.inspectflow.domain.common.enums.PartCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateInspectionItemRequest(
        @NotBlank
        String title,
        @NotBlank
        String description,
        @NotNull
        PartCategory category,
        @NotNull
        boolean impedimentItem
) {

}
