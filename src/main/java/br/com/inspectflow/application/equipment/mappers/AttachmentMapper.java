package br.com.inspectflow.application.equipment.mappers;

import br.com.inspectflow.application.equipment.dto.EquipmentAttachmentRequest;
import br.com.inspectflow.domain.bucket.dto.UploadResponse;
import br.com.inspectflow.domain.equipment.models.EquipmentAttachment;

public class AttachmentMapper {

    public static EquipmentAttachment toAttachment(EquipmentAttachmentRequest dto, UploadResponse uploadResponse) {
       return EquipmentAttachment.builder()
               .type(dto.type())
               .fileName(uploadResponse.fileName())
               .fileUrl(uploadResponse.fileUrl())
               .contentType(dto.file().getContentType())
               .build();

    }
}
