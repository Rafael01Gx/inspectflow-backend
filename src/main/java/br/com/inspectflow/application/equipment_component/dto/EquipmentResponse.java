package br.com.inspectflow.application.equipment_component.dto;

import br.com.inspectflow.domain.equipment.enums.EquipmentStatus;
import br.com.inspectflow.domain.equipment.enums.EquipmentType;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment_component.models.EquipmentComponent;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record EquipmentResponse(
        UUID id,
        String name,
        String code,
        EquipmentStatus status,
        EquipmentType type,
        String location,
        LocalDateTime lastInspection,
        LocalDateTime nextInspection,
        Set<EquipmentComponent> components
) {
    public static EquipmentResponse from(Equipment equipment) {
        return new EquipmentResponse(
                equipment.getId(),
                equipment.getName(),
                equipment.getCode(),
                equipment.getStatus(),
                equipment.getType(),
                equipment.getLocation(),
                equipment.getLastInspection(),
                equipment.getNextInspection(),
                equipment.getComponents()
        );
    }
}
