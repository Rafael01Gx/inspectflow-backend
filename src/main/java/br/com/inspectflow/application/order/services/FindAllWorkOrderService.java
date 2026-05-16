package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.order.dto.OrderListAllResponse;
import br.com.inspectflow.application.order.dto.OrderResponse;
import br.com.inspectflow.application.order.ports.in.FindAllWorkOrderUseCase;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllWorkOrderService implements FindAllWorkOrderUseCase {
    private final WorkOrderRepository repository;

    @Override
    @Transactional(readOnly = true)
    @Observed(name = "order.list-page",
            contextualName = "lista paginada de ordens")
    @Cacheable(value = "findAllWorkOrder", key = "':p:' + #pageRequest.page.toString()) + ':s:' + #pageRequest.size.toString()'")
    public PagedResponse<OrderResponse> execute(PageRequest pageRequest) {
        var page = repository.findAll(pageRequest);

        page.content().forEach(order -> IO.println(order.getOrderPriority().toString()));
        return new PagedResponse<OrderResponse>(
                page.content().stream().map(OrderResponse::from).toList(),
                page.pageNumber(),
                page.pageSize(),
                page.totalElements(),
                page.totalPages(),
                page.isLast()
        );
    }

    @Override
    @Observed(name = "order.list-all",
            contextualName = "lista completa de ordens")
    @Cacheable(value = "findAllWorkOrder", key = "'list'")
    public List<OrderListAllResponse> execute() {
        return repository.findAll().stream().map(OrderListAllResponse::from).toList();
    }
}
