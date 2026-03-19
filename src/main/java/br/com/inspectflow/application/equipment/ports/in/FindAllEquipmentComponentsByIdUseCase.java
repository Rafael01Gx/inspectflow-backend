package br.com.inspectflow.application.equipment.ports.in;

import br.com.inspectflow.domain.equipment.models.EquipmentComponent;

import java.util.List;
import java.util.UUID;

public interface FindAllEquipmentComponentsByIdUseCase {
    List<EquipmentComponent> execute(List<UUID> equipmentsIds);
}
