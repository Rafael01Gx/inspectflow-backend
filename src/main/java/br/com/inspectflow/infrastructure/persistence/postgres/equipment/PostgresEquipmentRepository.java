package br.com.inspectflow.infrastructure.persistence.postgres.equipment;

import br.com.inspectflow.domain.equipment.models.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostgresEquipmentRepository extends JpaRepository<Equipment, UUID> {

    @EntityGraph(attributePaths = {
            "components",
            "components.inspectionItem",
            "partsInStock",
            "attachments",
            "consignmentCodes",
            "healthSheet"
    })
    Optional<Equipment> findByCode(String code);

    @Override
    @EntityGraph(attributePaths = {
            "components",
            "attachments",
            "consignmentCodes",
            "healthSheet"
    })
    List<Equipment> findAll();

    boolean existsByCode(String code);

    List<Equipment> findAllByCodeIn(List<String> code);

    List<Equipment> findTop10ByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(String q, String q1);

    @Query("SELECT e.status, COUNT(e) FROM Equipment e GROUP BY e.status")
    List<Object[]> countEquipmentsByStatus();

    @EntityGraph(attributePaths = {
            "components",
            "components.inspectionItem",
            "partsInStock",
            "attachments",
            "consignmentCodes",
            "healthSheet"
    })
    Optional<Equipment> findById(UUID id);

    @Override
    Page<Equipment> findAll(Pageable pageable);
}
