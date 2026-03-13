package br.com.inspectflow.domain.equipment_component.repository;

import br.com.inspectflow.domain.equipment_component.models.EquipmentComponent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EquipmentComponentRepository extends JpaRepository<EquipmentComponent, UUID> {
}
