package br.com.inspectflow.application.Inspection.dto;

import br.com.inspectflow.domain.common.enums.PartCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateInspectionItemRequest(

        Long id,
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
