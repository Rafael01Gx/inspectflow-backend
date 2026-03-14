package br.com.inspectflow.domain.inspection.models;

import br.com.inspectflow.domain.Inspection_item.enums.InspectionStatus;
import lombok.Builder;

@Builder
public record InspectionItemResult(
        String title,
        String description,
        InspectionStatus status,
        String observation
) {
}
