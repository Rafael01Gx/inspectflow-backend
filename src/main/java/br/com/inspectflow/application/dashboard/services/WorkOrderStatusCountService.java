package br.com.inspectflow.application.dashboard.services;

import br.com.inspectflow.application.dashboard.dto.WorkOrderStatusCountDto;
import br.com.inspectflow.application.dashboard.ports.in.WorkOrderStatusCountUseCase;
import br.com.inspectflow.domain.order.enums.OrderStatus;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkOrderStatusCountService implements WorkOrderStatusCountUseCase {
    private final WorkOrderRepository workOrderRepository;

    @Override
    @Cacheable(value = "dashboardWorkOrderMonthlyStatusCounts", key = "'statusCounts'")
    @Transactional(readOnly = true)
    @Observed(name = "dashboard.order-status-count",
    contextualName = "contagem de ordens por status")
    public List<WorkOrderStatusCountDto> execute() {
        return workOrderRepository.countWorkOrdersByStatus().stream()
                .map(result -> {
                    OrderStatus status = (result[0] instanceof OrderStatus) ?
                            (OrderStatus) result[0] : OrderStatus.valueOf(result[0].toString());
                    return new WorkOrderStatusCountDto(status, ((Number) result[1]).longValue());
                })
                .collect(Collectors.toList());
    }

}
