package br.com.inspectflow.application.equipment.ports.in;

import br.com.inspectflow.application.equipment.dto.EquipmentResponse;

import java.util.UUID;

public interface FindByIdEquipmentUseCase {
    EquipmentResponse execute(UUID id);
}
