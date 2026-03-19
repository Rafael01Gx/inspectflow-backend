package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.equipment.models.EquipmentComponent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostgresEquipmentComponentRepository extends JpaRepository<EquipmentComponent, UUID> {
    List<EquipmentComponent> findAllByEquipmentIdIn(List<UUID> equipmentId);
}
