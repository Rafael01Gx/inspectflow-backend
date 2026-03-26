package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.order.dto.CancelOrderRequest;
import br.com.inspectflow.application.order.ports.in.CancelWorkOrderUseCase;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelWorkOrderService implements CancelWorkOrderUseCase {
    private final WorkOrderRepository repository;

    @Override
    public void execute(UUID id, CancelOrderRequest dto) {

    }
}
