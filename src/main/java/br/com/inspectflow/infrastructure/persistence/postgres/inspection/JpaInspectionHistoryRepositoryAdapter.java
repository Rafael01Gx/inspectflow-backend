package br.com.inspectflow.infrastructure.persistence.postgres.inspection;

import br.com.inspectflow.domain.inspection.models.InspectionHistory;
import br.com.inspectflow.domain.inspection.repositories.InspectionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JpaInspectionHistoryRepositoryAdapter implements InspectionHistoryRepository {

    private final PostgresInspectionHistoryRepository repository;

    @Override
    public List<InspectionHistory> findTop100ByEquipmentIdOrderByDateDesc(UUID equipmentId) {
        return repository.findTop100ByEquipmentIdOrderByDateDesc(equipmentId);
    }

    @Override
    public void save(InspectionHistory historico) {
        repository.save(historico);
    }
}
