package br.com.inspectflow.application.Inspection_item.dto;

import br.com.inspectflow.domain.Inspection_item.enums.InspectionCategoryItem;
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
        InspectionCategoryItem category,
        @NotNull
        boolean impedimentItem
) {

}
