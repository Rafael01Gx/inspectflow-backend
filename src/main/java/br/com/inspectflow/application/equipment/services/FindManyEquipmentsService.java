package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.equipment.EquipmentService;
import br.com.inspectflow.application.equipment.ports.in.FindManyEquipmentsUseCase;
import br.com.inspectflow.application.equipment.validators.AllEquipmentsExistValidator;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindManyEquipmentsService implements FindManyEquipmentsUseCase {
    private final EquipmentRepository repository;
    private final AllEquipmentsExistValidator validator;

    @Override
    public List<Equipment> execute(List<UUID> ids) {
        if(ids != null && !ids.isEmpty()){
            validator.execute(ids);
            return repository.findAllById(ids);
        }
        return List.of();
    }
}
