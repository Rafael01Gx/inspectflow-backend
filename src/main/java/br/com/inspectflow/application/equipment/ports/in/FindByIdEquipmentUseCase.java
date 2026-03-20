package br.com.inspectflow.application.equipment.ports.in;

import br.com.inspectflow.application.equipment.dto.EquipmentDetailsResponse;

import java.util.UUID;

public interface FindByIdEquipmentUseCase {
    EquipmentDetailsResponse execute(UUID id);
}
