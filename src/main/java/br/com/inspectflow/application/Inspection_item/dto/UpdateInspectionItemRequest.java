package br.com.inspectflow.application.Inspection_item.dto;

import br.com.inspectflow.domain.Inspection_item.enums.InspectionCategoryItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateInspectionItemRequest(
        @NotNull
        Long id,
        @NotBlank
        String title,
        @NotBlank
        String description,
        @NotNull
        InspectionCategoryItem category,
        @NotNull
        boolean impedimentItem,

        String observation
) {
}
