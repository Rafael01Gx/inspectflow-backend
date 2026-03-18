package br.com.inspectflow.application.equipment_component.ports.in;

import br.com.inspectflow.application.equipment_component.dto.CreateEquipmentComponentRequest;
import br.com.inspectflow.domain.equipment_component.models.EquipmentComponent;

public interface CreateEquipmentComponentUseCase {
    EquipmentComponent execute(CreateEquipmentComponentRequest dto);

}
