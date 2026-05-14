package br.com.inspectflow.application.equipment.dto;

import br.com.inspectflow.domain.equipment.enums.EquipmentStatus;
import br.com.inspectflow.domain.equipment.enums.EquipmentType;
import br.com.inspectflow.domain.equipment.models.Equipment;

import java.util.UUID;

public record EquipmentResponse(
        UUID id,
        String name,
        String code,
        EquipmentStatus status,
        EquipmentType type,
        String location,
        String imageUrl,
        String propertyCode
) {
    public static EquipmentResponse from(Equipment equipment) {
        return new EquipmentResponse(
                equipment.getId(),
                equipment.getName(),
                equipment.getCode(),
                equipment.getStatus(),
                equipment.getType(),
                equipment.getLocation(),
                equipment.getImageUrl(),
                equipment.getPropertyCode()
        );
    }
}
