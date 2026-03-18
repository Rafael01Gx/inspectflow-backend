package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.equipment.models.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostgresEquipmentRepository extends JpaRepository<Equipment, UUID> {
    Optional<Equipment> findByCode(String code);

    List<Equipment> findAllByCodeIn(List<String> code);
}
