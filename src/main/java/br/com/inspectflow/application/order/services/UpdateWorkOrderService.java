package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.common.validators.IdConsistencyValidator;
import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.http.handlers.WorkerOrderNotFoundException;
import br.com.inspectflow.application.order.dto.OrderResponse;
import br.com.inspectflow.application.order.dto.UpdateOrderRequest;
import br.com.inspectflow.application.order.helpers.SetInfoStockMessage;
import br.com.inspectflow.application.order.ports.in.UpdateWorkOrderUseCase;
import br.com.inspectflow.application.order.validators.WorkOrderUpdatePermissionValidator;
import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateWorkOrderService implements UpdateWorkOrderUseCase {
    private final WorkOrderRepository repository;
    private final UserRepository userRepository;
    private final WorkOrderUpdatePermissionValidator updatePermissionValidator;
    private final IdConsistencyValidator<UUID> idConsistencyValidator;
    private final SetInfoStockMessage setInfoStockMessage;


    @Override
    @Transactional
    public OrderResponse execute(UUID id, UpdateOrderRequest dto, Authentication authUser) {

        idConsistencyValidator.execute(id, dto.id());

        WorkOrder order = repository.findById(id).orElseThrow(WorkerOrderNotFoundException::new);

        User user = userRepository.findByEmail(authUser.getName()).orElseThrow(UserNotFoundException::new);

        updatePermissionValidator.execute(order, user);

        order.update(dto.title(), dto.description(), dto.orderPriority(), dto.dueDate(), dto.parts(), dto.completionDate(), user);

        setInfoStockMessage.execute(order);


        return OrderResponse.from(order);
    }
}
