package br.com.inspectflow.infrastructure.persistence.postgres.equipment;

import br.com.inspectflow.domain.equipment.models.EquipmentHealthSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PostgresEquipmentHealthRepository extends JpaRepository<EquipmentHealthSheet, UUID> {
    @Query(value = """
    SELECT
        (
            COUNT(next_mechanical_inspection)
            FILTER (
                WHERE next_mechanical_inspection BETWEEN :now AND :futureDate
            )
            +

            COUNT(next_electrical_inspection)
            FILTER (
                WHERE next_electrical_inspection BETWEEN :now AND :futureDate
            )
            +

            COUNT(next_calibration)
            FILTER (
                WHERE next_calibration BETWEEN :now AND :futureDate
            )
        )
    FROM equipment_health_sheets
    """, nativeQuery = true)
    Long countUpcomingInspections(
            @Param("now") LocalDateTime now,
            @Param("futureDate") LocalDateTime futureDate
    );
}
