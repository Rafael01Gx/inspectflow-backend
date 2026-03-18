package br.com.inspectflow.application.equipment.ports.in;

import br.com.inspectflow.domain.equipment.models.Equipment;

import java.util.List;

public interface FindManyEquipmentsByCodeUseCase {
    List<Equipment> execute(List<String> codes);

}
