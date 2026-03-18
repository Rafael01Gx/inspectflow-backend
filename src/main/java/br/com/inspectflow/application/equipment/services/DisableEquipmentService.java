package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.equipment.ports.in.DisableEquipmentUseCase;
import br.com.inspectflow.application.http.handlers.EquipmentNotFoundException;
import br.com.inspectflow.domain.equipment.enums.EquipmentStatus;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisableEquipmentService implements DisableEquipmentUseCase {

    private final EquipmentRepository repository;

    @Override
    public void execute(UUID id) {
        var equipment = repository.findById(id).orElseThrow(EquipmentNotFoundException::new);

        equipment.setStatus(EquipmentStatus.DISABLED);

        repository.save(equipment);

    }
}
