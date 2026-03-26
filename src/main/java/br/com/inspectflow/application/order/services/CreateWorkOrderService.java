package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.order.dto.CreateOrderRequest;
import br.com.inspectflow.application.order.dto.OrderResponse;
import br.com.inspectflow.application.order.ports.in.CreateWorkOrderUseCase;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateWorkOrderService implements CreateWorkOrderUseCase {
    private final WorkOrderRepository repository;

    @Override
    public OrderResponse execute(CreateOrderRequest dto) {
        return null;
    }
}
