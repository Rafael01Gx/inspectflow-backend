package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.order.dto.OrderResponse;
import br.com.inspectflow.application.order.dto.SearchOrderFilterRequest;
import br.com.inspectflow.application.order.ports.in.SearchWorkOrderUseCase;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchWorkOrderService implements SearchWorkOrderUseCase {
    private final WorkOrderRepository repository;

    @Override
    @Transactional(readOnly = true)
    @Observed(name = "order.search",
            contextualName = "busca ordens por filtro")
    public PagedResponse<OrderResponse> execute(SearchOrderFilterRequest filter, PageRequest pageRequest) {
        var page = repository.search(filter, pageRequest);

        return new PagedResponse<>(
                page.content().stream().map(OrderResponse::from).toList(),
                page.pageNumber(),
                page.pageSize(),
                page.totalElements(),
                page.totalPages(),
                page.isLast()
        );
    }
}
