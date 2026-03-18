package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.equipment.ports.in.FindByEquipmentCodeUseCase;
import br.com.inspectflow.application.equipment_component.dto.EquipmentResponse;
import br.com.inspectflow.application.http.handlers.EquipmentNotFoundException;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindByEquipmentCodeService implements FindByEquipmentCodeUseCase {
    private final EquipmentRepository equipmentRepository;

    @Override
    public EquipmentResponse execute(String code) {
        var equipment = equipmentRepository.findByCode(code).orElseThrow(EquipmentNotFoundException::new);
        return EquipmentResponse.from(equipment);
    }
}
