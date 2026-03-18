package br.com.inspectflow.application.equipment.ports.in;

import br.com.inspectflow.application.equipment.dto.CreateEquipmentRequest;
import br.com.inspectflow.application.equipment.dto.EquipmentResponse;

public interface CreateEquipmentUseCase {

    EquipmentResponse execute(CreateEquipmentRequest dto);

}
