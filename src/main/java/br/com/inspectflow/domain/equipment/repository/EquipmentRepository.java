package br.com.inspectflow.domain.equipment.repository;

import br.com.inspectflow.domain.equipment.models.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {
}
