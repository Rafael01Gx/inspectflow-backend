package br.com.inspectflow.application.equipment.ports.in;

import br.com.inspectflow.application.equipment_component.dto.EquipmentResponse;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;

import java.awt.print.Pageable;

public interface FindAllEquipmentUseCase {
    PagedResponse<EquipmentResponse> execute(PageRequest pageable);
}
