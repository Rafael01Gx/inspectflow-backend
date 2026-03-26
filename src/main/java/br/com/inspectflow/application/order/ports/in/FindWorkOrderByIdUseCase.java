package br.com.inspectflow.application.order.ports.in;

import br.com.inspectflow.application.order.dto.OrderResponse;

import java.util.UUID;

public interface FindWorkOrderByIdUseCase {

    OrderResponse execute(UUID id);

}
