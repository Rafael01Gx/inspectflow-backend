package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.http.handlers.WorkerOrderNotFoundException;
import br.com.inspectflow.application.order.dto.OrderResponse;
import br.com.inspectflow.application.order.ports.in.FindWorkOrderByIdUseCase;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindWorkOrderByIdService implements FindWorkOrderByIdUseCase {
    private final WorkOrderRepository repository;

    @Override
    public OrderResponse execute(UUID id) {
        return repository.findById(id).map(OrderResponse::from).orElseThrow(WorkerOrderNotFoundException::new);
    }
}
