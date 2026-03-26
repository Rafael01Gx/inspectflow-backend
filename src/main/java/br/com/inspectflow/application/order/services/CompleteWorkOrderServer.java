package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.order.dto.CompleteOrderRequest;
import br.com.inspectflow.application.order.dto.OrderResponse;
import br.com.inspectflow.application.order.ports.in.CompleteWorkOrderUseCase;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompleteWorkOrderServer implements CompleteWorkOrderUseCase {
    private final WorkOrderRepository repository;

    @Override
    public OrderResponse execute(UUID id, CompleteOrderRequest dto) {
        return null;
    }
}
