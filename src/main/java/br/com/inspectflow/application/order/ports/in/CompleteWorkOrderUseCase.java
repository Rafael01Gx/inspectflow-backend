package br.com.inspectflow.application.order.ports.in;

import br.com.inspectflow.application.order.dto.CompleteOrderRequest;
import br.com.inspectflow.application.order.dto.OrderResponse;

import java.util.UUID;

public interface CompleteWorkOrderUseCase {

    OrderResponse execute(UUID id, CompleteOrderRequest dto);
}
