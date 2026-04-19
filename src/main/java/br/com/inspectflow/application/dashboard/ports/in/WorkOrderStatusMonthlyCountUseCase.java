package br.com.inspectflow.application.dashboard.ports.in;

import br.com.inspectflow.application.dashboard.dto.WorkOrderStatusMonthlyCountDto;

import java.util.List;

public interface WorkOrderStatusMonthlyCountUseCase {
    List<WorkOrderStatusMonthlyCountDto> execute();
}
