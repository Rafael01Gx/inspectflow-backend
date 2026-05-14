package br.com.inspectflow.application.equipment.dto;

import br.com.inspectflow.domain.common.enums.PartCategory;
import br.com.inspectflow.domain.equipment.enums.EquipmentStatus;
import br.com.inspectflow.domain.equipment.enums.EquipmentType;
import br.com.inspectflow.domain.equipment.models.Equipment;

import java.util.Map;
import java.util.Optional;
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
        Set<EquipmentComponentResponse> components,
        Set<EquipmentAttachmentResponse> attachments,
        Map<PartCategory, String> consignmentCodes,
        String imageUrl,
        String propertyCode,
        EquipmentHealthSheetResponse healthSheet
) {
    public static EquipmentDetailsResponse from(Equipment equipment) {
        return new EquipmentDetailsResponse(
                equipment.getId(),
                equipment.getName(),
                equipment.getCode(),
                equipment.getStatus(),
                equipment.getType(),
                equipment.getLocation(),
                equipment.getComponents().stream().map(EquipmentComponentResponse::from).collect(Collectors.toSet()),
                equipment.getAttachments().stream()
                        .map(EquipmentAttachmentResponse::from)
                        .collect(Collectors.toSet()),
                equipment.getConsignmentCodes(),
                equipment.getImageUrl(),
                equipment.getPropertyCode(),
                Optional.ofNullable(equipment.getHealthSheet()).map(EquipmentHealthSheetResponse::from).orElse(null)
        );
    }
}
