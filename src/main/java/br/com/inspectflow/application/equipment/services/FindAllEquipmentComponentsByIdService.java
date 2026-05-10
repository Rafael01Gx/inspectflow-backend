package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.equipment.ports.in.FindAllEquipmentComponentsByIdUseCase;
import br.com.inspectflow.domain.equipment.models.EquipmentComponent;
import br.com.inspectflow.domain.equipment.repositories.EquipmentComponentRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindAllEquipmentComponentsByIdService implements FindAllEquipmentComponentsByIdUseCase {

    private final EquipmentComponentRepository repository;

    @Override
    @Transactional(readOnly = true)
    @Observed(name = "equipment.list-components",
            contextualName = "Lista componentes de um equipamento")
    public List<EquipmentComponent> execute(List<UUID> equipmentsIds) {
        if (equipmentsIds == null || equipmentsIds.isEmpty()) {
            return List.of();
        }
        return repository.findAllByEquipmentIdIn(equipmentsIds);
    }
}
