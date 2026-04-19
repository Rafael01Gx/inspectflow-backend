package br.com.inspectflow.application.dashboard.ports.in;

import br.com.inspectflow.application.dashboard.dto.WorkOrderStatusCountDto;

import java.util.List;

public interface WorkOrderStatusCountUseCase {
    List<WorkOrderStatusCountDto> execute();
}
