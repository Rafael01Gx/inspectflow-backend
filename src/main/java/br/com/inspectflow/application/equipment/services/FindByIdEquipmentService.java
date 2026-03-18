package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.equipment.ports.in.FindByIdEquipmentUseCase;
import br.com.inspectflow.application.equipment_component.dto.EquipmentResponse;
import br.com.inspectflow.application.http.handlers.EquipmentNotFoundException;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindByIdEquipmentService implements FindByIdEquipmentUseCase {

    private final EquipmentRepository repository;

    @Override
    public EquipmentResponse execute(UUID id) {
        var equipment = repository.findById(id).orElseThrow(EquipmentNotFoundException::new);
        return EquipmentResponse.from(equipment);
    }
}
