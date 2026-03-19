package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.equipment.ports.in.FindAllEquipmentComponentsByIdUseCase;
import br.com.inspectflow.domain.equipment.models.EquipmentComponent;
import br.com.inspectflow.domain.equipment.repositories.EquipmentComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindAllEquipmentComponentsByIdService implements FindAllEquipmentComponentsByIdUseCase {

    private final EquipmentComponentRepository repository;

    @Override
    public List<EquipmentComponent> execute(List<UUID> equipmentsIds) {
        if (equipmentsIds == null || equipmentsIds.isEmpty()) {
            return List.of();
        }
        return repository.findAllByEquipmentIdIn(equipmentsIds);
    }
}
