package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.common.validators.IdConsistencyValidator;
import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.http.handlers.WorkerOrderNotFoundException;
import br.com.inspectflow.application.notification.templates.CancelOrderNotification;
import br.com.inspectflow.application.order.dto.CancelOrderRequest;
import br.com.inspectflow.application.order.ports.in.CancelWorkOrderUseCase;
import br.com.inspectflow.application.order.validators.WorkOrderUpdatePermissionValidator;
import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelWorkOrderService implements CancelWorkOrderUseCase {
    private final WorkOrderRepository repository;
    private final UserRepository userRepository;
    private final IdConsistencyValidator<UUID> idConsistencyValidator;
    private final WorkOrderUpdatePermissionValidator permissionValidator;
    private final CancelOrderNotification notification;

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "dashboardWorkOrders", key = "'statusCounts'"),
            @CacheEvict(value = "dashboardKpis", key = "'summary'")
    })
    public void execute(UUID id, CancelOrderRequest dto, Authentication authUser) {


        idConsistencyValidator.execute(id, dto.id());

        WorkOrder workOrder = repository.findById(id).orElseThrow(WorkerOrderNotFoundException::new);

        User user = userRepository.findByEmail(authUser.getName()).orElseThrow(UserNotFoundException::new);

        permissionValidator.execute(workOrder, user);

        workOrder.addSystemInfo("Ordem de serviço cancelada por: " + user.getName());
        workOrder.addSystemInfo("Justificativa: " + dto.justification());
        workOrder.setPerformedWork("Ordem de serviço cancelada por: " + user.getName() + " - Justificativa: " + dto.justification());

        workOrder.cancelOrder();

        repository.save(workOrder);

        notification.execute(workOrder);
    }
}
