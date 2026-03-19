package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.equipment.dto.UpdateEquipmentComponentRequest;
import br.com.inspectflow.application.equipment.ports.in.UpdateEquipmentComponentUseCase;
import br.com.inspectflow.application.http.handlers.EquipmentComponentNotFoundExceprion;
import br.com.inspectflow.domain.equipment.models.EquipmentComponent;
import br.com.inspectflow.domain.equipment.repositories.EquipmentComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateEquipmentComponentService implements UpdateEquipmentComponentUseCase {
    private final EquipmentComponentRepository repository;

    @Override
    @Transactional
    public EquipmentComponent execute(UUID id, UpdateEquipmentComponentRequest dto) {
        var component = repository.findById(id).orElseThrow(EquipmentComponentNotFoundExceprion::new);

        component.update(dto.name(), dto.category());

        return repository.save(component);
    }

}
