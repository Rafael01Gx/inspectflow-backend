package br.com.inspectflow.infrastructure.persistence.postgres.inspection;

import br.com.inspectflow.domain.inspection.models.InspectionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostgresInspectionHistoryRepository extends JpaRepository<InspectionHistory, UUID> {

    List<InspectionHistory> findTop100ByEquipmentIdOrderByDateDesc(UUID equipmentId);

}
