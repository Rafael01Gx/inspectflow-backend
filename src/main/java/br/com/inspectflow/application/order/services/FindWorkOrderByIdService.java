package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.http.handlers.WorkerOrderNotFoundException;
import br.com.inspectflow.application.order.dto.OrderDetailResponse;
import br.com.inspectflow.application.order.ports.in.FindWorkOrderByIdUseCase;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindWorkOrderByIdService implements FindWorkOrderByIdUseCase {
    private final WorkOrderRepository repository;

    @Override
    @Transactional(readOnly = true)
    @Observed(name = "order.find-id",
            contextualName = "busca ordens por id")
    public OrderDetailResponse execute(UUID id) {
        return repository.findById(id).map(OrderDetailResponse::from).orElseThrow(WorkerOrderNotFoundException::new);
    }
}
