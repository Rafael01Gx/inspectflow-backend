package br.com.inspectflow.application.order.ports.in;

import br.com.inspectflow.application.order.dto.OrderListAllResponse;
import br.com.inspectflow.application.order.dto.OrderResponse;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;

import java.util.List;

public interface FindAllWorkOrderUseCase {

    PagedResponse<OrderResponse> execute(PageRequest pageRequest);
    List<OrderListAllResponse> execute();
}
