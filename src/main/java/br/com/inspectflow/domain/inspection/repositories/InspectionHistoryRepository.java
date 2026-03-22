package br.com.inspectflow.domain.inspection.repositories;

import br.com.inspectflow.domain.inspection.models.InspectionHistory;

import java.util.List;
import java.util.UUID;

public interface InspectionHistoryRepository {

    List<InspectionHistory> findTop100ByEquipmentIdOrderByDateDesc(UUID equipmentId);

    void save(InspectionHistory historico);
}
