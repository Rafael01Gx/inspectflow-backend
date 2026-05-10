package br.com.inspectflow.application.equipment.dto;

import br.com.inspectflow.domain.equipment.enums.EquipmentStatus;
import br.com.inspectflow.domain.equipment.enums.EquipmentType;
import br.com.inspectflow.domain.equipment.enums.InspectionFrequency;
import br.com.inspectflow.domain.equipment.models.Equipment;

import java.time.LocalDateTime;
import java.util.UUID;

public record EquipmentSummaryResponse(
        UUID id,
        String name,
        String code,
        EquipmentStatus status,
        EquipmentType type,
        String location,
        InspectionFrequency inspectionFrequency,
        LocalDateTime lastInspection,
        LocalDateTime nextInspection,
        String imageUrl,
        String propertyCode
) {
    public static EquipmentSummaryResponse from(Equipment e) {
        return new EquipmentSummaryResponse(
                e.getId(), e.getName(), e.getCode(),
                e.getStatus(), e.getType(), e.getLocation(),
                e.getInspectionFrequency(), e.getLastInspection(),
                e.getNextInspection(),e.getImageUrl(), e.getPropertyCode()
        );
    }
}
