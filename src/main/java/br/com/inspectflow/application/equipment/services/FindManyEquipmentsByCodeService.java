package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.equipment.ports.in.FindManyEquipmentsByCodeUseCase;
import br.com.inspectflow.application.equipment.validators.AllEquipmentCodeExistValidator;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindManyEquipmentsByCodeService implements FindManyEquipmentsByCodeUseCase {
    private final EquipmentRepository repository;
    private final AllEquipmentCodeExistValidator validator;


    @Override
    public List<Equipment> execute(List<String> codes) {
        if(codes != null && !codes.isEmpty()){
            validator.execute(codes);
            return repository.findAllByCodeIn(codes);
        }
        return List.of();
    }
}
