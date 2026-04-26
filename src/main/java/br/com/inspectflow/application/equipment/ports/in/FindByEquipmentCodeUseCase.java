package br.com.inspectflow.application.equipment.ports.in;

import br.com.inspectflow.application.equipment.dto.EquipmentDetailsResponse;

public interface FindByEquipmentCodeUseCase {
    EquipmentDetailsResponse execute(String code);

}
