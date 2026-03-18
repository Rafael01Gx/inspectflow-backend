package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.common.validators.IdConsistencyValidator;
import br.com.inspectflow.application.equipment.dto.EquipmentResponse;
import br.com.inspectflow.application.equipment.dto.UpdateEquipmentRequest;
import br.com.inspectflow.application.equipment.ports.in.UpdateEquipmentUseCase;
import br.com.inspectflow.application.http.handlers.EquipmentNotFoundException;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateEquipmentService implements UpdateEquipmentUseCase {
    private final EquipmentRepository repository;
    private final IdConsistencyValidator<UUID> idValidator;

    @Override
    @Transactional
    public EquipmentResponse execute(UUID id, UpdateEquipmentRequest dto) {
       idValidator.execute(id,dto.id());
       var equipment = repository.findById(id).orElseThrow(EquipmentNotFoundException::new);

       equipment.update(dto.name(), dto.status(), dto.type(), dto.location());

        repository.save(equipment);

        return EquipmentResponse.from(equipment);
    }
}
