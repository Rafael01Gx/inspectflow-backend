package br.com.inspectflow.application.dashboard.ports.in;

import br.com.inspectflow.application.dashboard.dto.EquipmentStatusCountDto;

import java.util.List;

public interface EquipmentStatusCountUseCase {
    List<EquipmentStatusCountDto> execute();
}
