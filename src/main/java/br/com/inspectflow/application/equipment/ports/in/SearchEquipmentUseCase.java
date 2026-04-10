package br.com.inspectflow.application.equipment.ports.in;

import br.com.inspectflow.application.equipment.dto.EquipmentResponse;

import java.util.List;

public interface SearchEquipmentUseCase {

    List<EquipmentResponse> execute(String q);
}
