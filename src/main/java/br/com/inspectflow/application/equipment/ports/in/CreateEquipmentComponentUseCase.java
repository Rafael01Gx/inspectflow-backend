package br.com.inspectflow.application.equipment.ports.in;

import br.com.inspectflow.application.equipment.dto.CreateEquipmentComponentRequest;
import br.com.inspectflow.domain.equipment.models.EquipmentComponent;

public interface CreateEquipmentComponentUseCase {
    EquipmentComponent execute(CreateEquipmentComponentRequest dto);

}
