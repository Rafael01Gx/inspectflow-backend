package br.com.inspectflow.application.equipment.ports.in;

import br.com.inspectflow.application.equipment.dto.EquipmentResponse;

public interface FindByEquipmentCodeUseCase {
    EquipmentResponse execute(String code);

}
