package br.com.inspectflow.application.equipment_component.ports.in;

import br.com.inspectflow.domain.equipment_component.models.EquipmentComponent;

import java.util.List;
import java.util.UUID;

public interface FindAllEquipmentComponentsByIdUseCase {
    List<EquipmentComponent> execute(List<UUID> equipmentsIds);
}
