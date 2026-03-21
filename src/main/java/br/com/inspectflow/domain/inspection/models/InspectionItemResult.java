package br.com.inspectflow.domain.inspection.models;

import br.com.inspectflow.domain.inspection.enums.InspectionItemStatus;
import lombok.Builder;

@Builder
public record InspectionItemResult(
        String title,
        String description,
        InspectionItemStatus status,
        boolean impedimentItem,
        String observation
) {
}
