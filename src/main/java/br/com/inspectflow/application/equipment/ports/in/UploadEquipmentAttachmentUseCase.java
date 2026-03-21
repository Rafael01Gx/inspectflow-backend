package br.com.inspectflow.application.equipment.ports.in;

import br.com.inspectflow.application.equipment.dto.EquipmentAttachmentRequest;
import br.com.inspectflow.application.equipment.dto.EquipmentDetailsResponse;

import java.util.UUID;

public interface UploadEquipmentAttachmentUseCase {

    EquipmentDetailsResponse execute(UUID id, EquipmentAttachmentRequest dto);
}
