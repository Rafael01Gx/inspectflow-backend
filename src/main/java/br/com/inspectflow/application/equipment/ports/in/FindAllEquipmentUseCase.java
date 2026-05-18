package br.com.inspectflow.application.equipment.ports.in;

import br.com.inspectflow.application.equipment.dto.EquipmentListResponse;
import br.com.inspectflow.application.equipment.dto.EquipmentSummaryResponse;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;

import java.util.List;

public interface FindAllEquipmentUseCase {
    PagedResponse<EquipmentSummaryResponse> execute(PageRequest pageable);
    List<EquipmentListResponse> execute();
}
