package br.com.inspectflow.application.order.ports.in;

import br.com.inspectflow.application.order.dto.OrderResponse;
import br.com.inspectflow.application.order.dto.UpdateOrderRequest;

import java.util.UUID;

public interface UpdateWorkOrderUseCase {

    OrderResponse execute(UUID id, UpdateOrderRequest dto);
}
