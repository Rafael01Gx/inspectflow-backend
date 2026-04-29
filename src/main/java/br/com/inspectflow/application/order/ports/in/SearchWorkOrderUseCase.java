package br.com.inspectflow.application.order.ports.in;

import br.com.inspectflow.application.order.dto.OrderResponse;
import br.com.inspectflow.application.order.dto.SearchOrderFilterRequest;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;

public interface SearchWorkOrderUseCase {

    PagedResponse<OrderResponse> execute(SearchOrderFilterRequest filter, PageRequest pageRequest);
}
