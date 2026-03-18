package br.com.inspectflow.application.equipment_component.services;

import br.com.inspectflow.application.equipment_component.dto.UpdateEquipmentComponentRequest;
import br.com.inspectflow.application.equipment_component.ports.in.UpdateEquipmentComponentUseCase;
import br.com.inspectflow.application.http.handlers.EquipmentComponentNotFoundExceprion;
import br.com.inspectflow.domain.equipment_component.models.EquipmentComponent;
import br.com.inspectflow.domain.equipment_component.repositories.EquipmentComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateEquipmentComponentService implements UpdateEquipmentComponentUseCase {
    private final EquipmentComponentRepository repository;

    @Override
    public EquipmentComponent execute(UUID id, UpdateEquipmentComponentRequest dto) {
        var component = repository.findById(id).orElseThrow(EquipmentComponentNotFoundExceprion::new);

        component.update(dto.name(), dto.category());

        // Ainda tenho que implementar a lógica para atualizar os itens de inspeção...

        return repository.save(component);
    }

}
