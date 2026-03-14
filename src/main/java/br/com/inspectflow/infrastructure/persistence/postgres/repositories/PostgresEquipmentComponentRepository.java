package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.equipment_component.models.EquipmentComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PostgresEquipmentComponentRepository extends JpaRepository<EquipmentComponent, UUID> {
}
