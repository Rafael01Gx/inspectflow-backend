package br.com.inspectflow.application.Inspection.dto;

import br.com.inspectflow.domain.inspection.enums.InspectionCategoryItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateInspectionItemRequest(

        Long id,
        @NotBlank
        String title,
        @NotBlank
        String description,
        @NotNull
        InspectionCategoryItem category,
        @NotNull
        boolean impedimentItem
) {
}
