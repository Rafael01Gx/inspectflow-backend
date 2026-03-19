package br.com.inspectflow.application.equipment.ports.in;

import br.com.inspectflow.application.equipment.dto.EquipmentAttachmentRequest;

import java.util.UUID;

public interface UploadEquipmentAttachmentUseCase {

    void execute(UUID id, EquipmentAttachmentRequest dto);
}
