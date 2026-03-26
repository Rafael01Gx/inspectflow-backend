package br.com.inspectflow.application.order.ports.in;

import br.com.inspectflow.application.order.dto.CreateOrderRequest;
import br.com.inspectflow.application.order.dto.OrderResponse;

public interface CreateWorkOrderUseCase {

    OrderResponse execute(CreateOrderRequest dto);
}
