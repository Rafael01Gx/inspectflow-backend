package br.com.inspectflow.application.equipment.dto;

import br.com.inspectflow.domain.common.enums.PartCategory;
import br.com.inspectflow.domain.equipment.enums.EquipmentStatus;
import br.com.inspectflow.domain.equipment.enums.EquipmentType;
import br.com.inspectflow.domain.equipment.models.Equipment;
import lombok.Builder;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Builder
public record EquipmentListResponse(
        UUID id,
        String name,
        String code,
        EquipmentStatus status,
        EquipmentType type,
        String location,
        Integer components,
        Integer attachments,
        Map<PartCategory, String> consignmentCodes,
        String propertyCode,
        EquipmentHealthSheetResponse healthSheet
) {
    public static EquipmentListResponse from(Equipment equipment) {
        return  EquipmentListResponse.builder()
                .id(equipment.getId())
                .name(equipment.getName())
                .code(equipment.getCode())
                .status(equipment.getStatus())
                .type(equipment.getType())
                .location(equipment.getLocation())
                .components(equipment.getComponents().size())
                .attachments(equipment.getAttachments().size())
                .consignmentCodes(equipment.getConsignmentCodes())
                .propertyCode(equipment.getPropertyCode())
                .healthSheet(Optional.ofNullable(equipment.getHealthSheet()).map(EquipmentHealthSheetResponse::from).orElse(null))
                .build();
    }
}
