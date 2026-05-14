package br.com.inspectflow.application.equipment.dto;

import br.com.inspectflow.domain.common.enums.PartCategory;
import br.com.inspectflow.domain.inspection.models.InspectionItem;

public record InspectionItemResponse(
        Long id,
        String title,
        String description,
        PartCategory category,
        boolean impedimentItem
) {

    public static InspectionItemResponse from(InspectionItem inspectionItem) {
        return new InspectionItemResponse(
                inspectionItem.getId(),
                inspectionItem.getTitle(),
                inspectionItem.getDescription(),
                inspectionItem.getCategory(),
                inspectionItem.isImpedimentItem()
        );
    }
}
