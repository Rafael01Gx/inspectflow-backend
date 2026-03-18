package br.com.inspectflow.application.equipment.ports.in;

import br.com.inspectflow.application.equipment_component.dto.EquipmentResponse;

public interface FindByEquipmentCodeUseCase {
    EquipmentResponse execute(String code);

}
