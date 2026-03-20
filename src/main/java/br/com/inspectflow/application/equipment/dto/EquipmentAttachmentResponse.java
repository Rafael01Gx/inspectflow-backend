package br.com.inspectflow.application.equipment.dto;

import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import br.com.inspectflow.domain.equipment.models.EquipmentAttachment;
import lombok.Builder;

import java.util.UUID;

@Builder
public record EquipmentAttachmentResponse(
        UUID id,
        AttachmentType type,
        String fileName,
        String contentType
) {
    public static EquipmentAttachmentResponse from(EquipmentAttachment equipmentAttachment) {
        return new EquipmentAttachmentResponse(
                equipmentAttachment.getId(),
                equipmentAttachment.getType(),
                equipmentAttachment.getFileName(),
                equipmentAttachment.getContentType()
        );
    }
}
