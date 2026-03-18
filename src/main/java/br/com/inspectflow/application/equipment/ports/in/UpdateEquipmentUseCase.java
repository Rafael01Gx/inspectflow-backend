package br.com.inspectflow.application.equipment.ports.in;

import br.com.inspectflow.application.equipment.dto.EquipmentResponse;
import br.com.inspectflow.application.equipment.dto.UpdateEquipmentRequest;

import java.util.UUID;

public interface UpdateEquipmentUseCase {

    EquipmentResponse execute(UUID id, UpdateEquipmentRequest dto);

}
