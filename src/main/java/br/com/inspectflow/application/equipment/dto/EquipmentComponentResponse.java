package br.com.inspectflow.application.equipment.dto;

import br.com.inspectflow.domain.common.enums.PartCategory;
import br.com.inspectflow.domain.equipment.models.EquipmentComponent;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record EquipmentComponentResponse(
        UUID id,
        String name,
        PartCategory category,
        Set<InspectionItemResponse> inspectionItem
) {

    public static EquipmentComponentResponse from(EquipmentComponent equipmentComponent) {
        return new EquipmentComponentResponse(
                equipmentComponent.getId(),
                equipmentComponent.getName(),
                equipmentComponent.getCategory(),
                equipmentComponent.getInspectionItem().stream().map(InspectionItemResponse::from).collect(Collectors.toSet())

        );
    }
}
