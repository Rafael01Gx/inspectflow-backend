package br.com.inspectflow.application.equipment.ports.in;

import br.com.inspectflow.application.equipment.dto.UpdateEquipmentComponentRequest;
import br.com.inspectflow.domain.equipment.models.EquipmentComponent;

import java.util.UUID;

public interface UpdateEquipmentComponentUseCase {
    EquipmentComponent execute(UUID id, UpdateEquipmentComponentRequest dto);
}
