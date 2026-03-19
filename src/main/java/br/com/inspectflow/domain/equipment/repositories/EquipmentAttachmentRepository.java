package br.com.inspectflow.domain.equipment.repositories;

import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import br.com.inspectflow.domain.equipment.models.EquipmentAttachment;

import java.util.Optional;
import java.util.UUID;

public interface EquipmentAttachmentRepository {
    Optional<EquipmentAttachment> findById(UUID id);
    Optional<EquipmentAttachment> findByType(AttachmentType type);
    boolean existsByIdAndType(UUID id, AttachmentType type);
    boolean existsByEquipmentIdAndType(UUID equipmentId,AttachmentType type);
}
