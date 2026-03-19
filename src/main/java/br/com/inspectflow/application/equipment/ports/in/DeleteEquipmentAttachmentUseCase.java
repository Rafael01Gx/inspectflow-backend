package br.com.inspectflow.application.equipment.ports.in;

import java.util.UUID;

public interface DeleteEquipmentAttachmentUseCase {

    void execute(UUID equipmentId, UUID attachmentId);

}
