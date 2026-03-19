package br.com.inspectflow.application.equipment.mappers;

import br.com.inspectflow.application.equipment.dto.EquipmentAttachmentRequest;
import br.com.inspectflow.domain.bucket.dto.UploadRequest;
import br.com.inspectflow.domain.equipment.models.EquipmentAttachment;

public class AttachmentMapper {

    public static EquipmentAttachment toAttachment(EquipmentAttachmentRequest dto, UploadRequest uploadRequest) {
       return EquipmentAttachment.builder()
               .type(dto.type())
               .fileName(uploadRequest.fileName())
               .fileUrl(uploadRequest.fileUrl())
               .contentType(dto.file().getContentType())
               .build();

    }
}
