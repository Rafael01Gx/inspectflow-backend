package br.com.inspectflow.application.Inspection.services;

import br.com.inspectflow.application.Inspection.ports.in.FindByEquipmentIdUseCase;
import br.com.inspectflow.domain.inspection.models.InspectionHistory;
import br.com.inspectflow.domain.inspection.repositories.InspectionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindByEquipmentIdService implements FindByEquipmentIdUseCase {

    private final InspectionHistoryRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<InspectionHistory> execute(UUID equipmentId) {

        return repository.findTop100ByEquipmentIdOrderByDateDesc(equipmentId);
    }
}
