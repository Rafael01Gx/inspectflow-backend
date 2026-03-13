package br.com.inspectflow.domain.equipamento.repository;

import br.com.inspectflow.domain.equipamento.models.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {
}
