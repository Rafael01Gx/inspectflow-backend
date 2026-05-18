package br.com.inspectflow.application.order.ports.in;

import br.com.inspectflow.application.order.dto.OrderDetailResponse;

import java.util.UUID;

public interface FindWorkOrderByIdUseCase {

    OrderDetailResponse execute(UUID id);

}
