package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.order.dto.OrderResponse;
import br.com.inspectflow.application.order.dto.UpdateOrderRequest;
import br.com.inspectflow.application.order.ports.in.UpdateWorkOrderUseCase;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateWorkOrderService implements UpdateWorkOrderUseCase {
    private final WorkOrderRepository repository;

    @Override
    public OrderResponse execute(UUID id, UpdateOrderRequest dto) {
        return null;
    }
}
