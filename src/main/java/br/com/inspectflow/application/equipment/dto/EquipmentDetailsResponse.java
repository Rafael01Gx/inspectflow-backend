package br.com.inspectflow.application.equipment.dto;

import br.com.inspectflow.domain.common.enums.PartCategory;
import br.com.inspectflow.domain.equipment.enums.EquipmentStatus;
import br.com.inspectflow.domain.equipment.enums.EquipmentType;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.models.EquipmentComponent;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record EquipmentDetailsResponse(
        UUID id,
        String name,
        String code,
        EquipmentStatus status,
        EquipmentType type,
        String location,
        LocalDateTime lastInspection,
        LocalDateTime nextInspection,
        Set<EquipmentComponent> components,
        Set<EquipmentAttachmentResponse> attachments,
        Map<PartCategory, String> consignmentCodes
) {
    public static EquipmentDetailsResponse from(Equipment equipment) {
        return new EquipmentDetailsResponse(
                equipment.getId(),
                equipment.getName(),
                equipment.getCode(),
                equipment.getStatus(),
                equipment.getType(),
                equipment.getLocation(),
                equipment.getLastInspection(),
                equipment.getNextInspection(),
                equipment.getComponents(),
                equipment.getAttachments().stream()
                        .map(EquipmentAttachmentResponse::from)
                        .collect(Collectors.toSet()),
                equipment.getConsignmentCodes()
        );
    }
}
