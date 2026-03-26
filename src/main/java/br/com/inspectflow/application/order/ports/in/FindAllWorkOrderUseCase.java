package br.com.inspectflow.application.order.ports.in;

import br.com.inspectflow.application.order.dto.OrderResponse;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;

public interface FindAllWorkOrderUseCase {

    PagedResponse<OrderResponse> execute(PageRequest pageRequest);
}
