package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import br.com.inspectflow.domain.equipment.models.EquipmentAttachment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JpaEquipmentAttachmentRepositoryAdapter implements EquipmentAttachmentRepository {
    private final PostgresEquipmentAttachmentRepository repository;

    @Override
    public Optional<EquipmentAttachment> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Optional<EquipmentAttachment> findByType(AttachmentType type) {
        return repository.findByType(type);
    }

    @Override
    public boolean existsByIdAndType(UUID id, AttachmentType type) {
        return repository.existsByIdAndType(id, type);
    }

    @Override
    public boolean existsByEquipmentIdAndType(UUID equipmentId, AttachmentType type) {
        return repository.existsByEquipmentIdAndType(equipmentId, type);
    }
}
