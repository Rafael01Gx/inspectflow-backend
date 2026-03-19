package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.equipment.enums.AttachmentType;
import br.com.inspectflow.domain.equipment.models.EquipmentAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PostgresEquipmentAttachmentRepository extends JpaRepository<EquipmentAttachment, UUID> {

    Optional<EquipmentAttachment> findByType(AttachmentType type);

    @Query("SELECT COUNT(ea) > 0 FROM EquipmentAttachment ea WHERE ea.id = :id AND ea.type = :type")
    boolean existsByIdAndType(@Param("id") UUID id, @Param("type") AttachmentType type);

    @Query("SELECT COUNT(ea) > 0 FROM EquipmentAttachment ea WHERE ea.equipment.id = :equipmentId AND ea.type = :type")
    boolean existsByEquipmentIdAndType(@Param("equipmentId") UUID equipmentId, @Param("type") AttachmentType type);

}
