package br.com.inspectflow.application.equipment_component.ports.in;

import br.com.inspectflow.application.equipment_component.dto.UpdateEquipmentComponentRequest;
import br.com.inspectflow.domain.equipment_component.models.EquipmentComponent;

import java.util.UUID;

public interface UpdateEquipmentComponentUseCase {
    EquipmentComponent execute(UUID id, UpdateEquipmentComponentRequest dto);
}
