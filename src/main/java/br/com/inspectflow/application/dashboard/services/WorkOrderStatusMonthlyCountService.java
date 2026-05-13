package br.com.inspectflow.application.dashboard.services;

import br.com.inspectflow.application.dashboard.dto.WorkOrderStatusCountDto;
import br.com.inspectflow.application.dashboard.dto.WorkOrderStatusMonthlyCountDto;
import br.com.inspectflow.application.dashboard.ports.in.WorkOrderStatusMonthlyCountUseCase;
import br.com.inspectflow.domain.order.enums.OrderStatus;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkOrderStatusMonthlyCountService implements WorkOrderStatusMonthlyCountUseCase {
    private final WorkOrderRepository workOrderRepository;

    @Override
    @Cacheable(value = "dashboardWorkOrderStatusCounts", key = "'monthlyStatusCounts'")
    @Transactional(readOnly = true)
    @Observed(name = "dashboard.order-status-mouthly.count",
    contextualName = "contagem mensal de ordens por status")
    public List<WorkOrderStatusMonthlyCountDto> execute() {
        List<Object[]> results = workOrderRepository.countWorkOrdersByStatusMonthly();

        Map<String, Map<OrderStatus, Long>> monthlyStatusMap = results.stream()
                .collect(Collectors.groupingBy(
                        result -> ((Number) result[0]).intValue() + "-" + String.format("%02d", ((Number) result[1]).intValue()),
                        Collectors.groupingBy(
                                result -> (result[2] instanceof OrderStatus) ?
                                        (OrderStatus) result[2] : OrderStatus.valueOf(result[2].toString()),
                                Collectors.summingLong(result -> ((Number) result[3]).longValue())
                        )
                ));

        return monthlyStatusMap.entrySet().stream()
                .map(entry -> {
                    String[] ym = entry.getKey().split("-");
                    int year = Integer.parseInt(ym[0]);
                    int month = Integer.parseInt(ym[1]);
                    List<WorkOrderStatusCountDto> statusCounts = entry.getValue().entrySet().stream()
                            .map(statusEntry -> new WorkOrderStatusCountDto(statusEntry.getKey(), statusEntry.getValue()))
                            .collect(Collectors.toList());
                    return new WorkOrderStatusMonthlyCountDto(year, month, statusCounts);
                })
                .sorted(Comparator.comparing(WorkOrderStatusMonthlyCountDto::year).thenComparing(WorkOrderStatusMonthlyCountDto::month))
                .collect(Collectors.toList());
    }
}
