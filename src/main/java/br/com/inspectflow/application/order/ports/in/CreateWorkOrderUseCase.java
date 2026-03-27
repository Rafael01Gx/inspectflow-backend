package br.com.inspectflow.application.order.ports.in;

import br.com.inspectflow.application.order.dto.CreateOrderRequest;
import br.com.inspectflow.application.order.dto.OrderResponse;

import java.util.UUID;

public interface CreateWorkOrderUseCase {

    OrderResponse execute(UUID userId, CreateOrderRequest dto);
}
