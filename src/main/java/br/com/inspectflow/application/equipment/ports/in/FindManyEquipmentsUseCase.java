package br.com.inspectflow.application.equipment.ports.in;

import br.com.inspectflow.domain.equipment.models.Equipment;

import java.util.List;
import java.util.UUID;

public interface FindManyEquipmentsUseCase {

    List<Equipment> execute(List<UUID> ids);
}
