package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.http.handlers.WorkerOrderNotFoundException;
import br.com.inspectflow.application.notification.templates.CompleteOrderNotification;
import br.com.inspectflow.application.order.dto.CompleteOrderRequest;
import br.com.inspectflow.application.order.dto.OrderResponse;
import br.com.inspectflow.application.order.ports.in.CompleteWorkOrderUseCase;
import br.com.inspectflow.application.order.validators.WorkOrderUpdatePermissionValidator;
import br.com.inspectflow.application.stock.dto.DeductStockRequest;
import br.com.inspectflow.application.stock.services.DeductAllStockItemsService;
import br.com.inspectflow.domain.order.models.MaintenancePart;
import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import io.micrometer.observation.annotation.Observed;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompleteWorkOrderServer implements CompleteWorkOrderUseCase {
    private final WorkOrderRepository repository;
    private final UserRepository userRepository;
    private final WorkOrderUpdatePermissionValidator updatePermissionValidator;
    private final DeductAllStockItemsService deductAllStockItems;
    private final CompleteOrderNotification notification;

    @Override
    @Transactional
    @CacheEvict(value = {
            "personalSummary",
            "personalPendingOrders",
            "personalRecentCompleted",
            "personalWorkOrderTimeline",
            "dashboardWorkOrderStatusCounts",
            "plantHealthOpenOrders"
    }, allEntries = true)
    @Observed(name = "order.complete",
            contextualName = "completa uma ordem de serviço")
    public OrderResponse execute(UUID id, CompleteOrderRequest dto, Authentication authUser) {
        WorkOrder order = repository.findById(id).orElseThrow(WorkerOrderNotFoundException::new);
        User user = userRepository.findByEmail(authUser.getName()).orElseThrow(UserNotFoundException::new);

        updatePermissionValidator.execute(order, user);

        deductStock(dto.parts(), order);

        order.setPerformedWork(dto.performedWork());
        order.addSystemInfo("Ordem de serviço finalizada com sucesso por: " + user.getName());
        order.removeAllParts();
        order.addAllParts(dto.parts());
        order.completeOrder();

        notification.execute(order);

        return OrderResponse.from(order);
    }

    private void deductStock(List<MaintenancePart> parts, WorkOrder order) {
        if (parts == null || parts.isEmpty()) return;

        List<DeductStockRequest> requests = new ArrayList<>();
        boolean headerAdded = false;

        for (MaintenancePart part : parts) {
            if (part.isFromStock() && part.stockId() != null) {
                requests.add(new DeductStockRequest(part.stockId(), part.quantity()));
            } else {
                if (!headerAdded) {
                    order.addSystemInfo("Foram utilizados os seguintes itens: ");
                    headerAdded = true;
                }
                order.addSystemInfo(part.name().toUpperCase() + " - " + part.quantity() + " unidades");
            }
        }
        if (!requests.isEmpty()) {
            deductAllStockItems.execute(requests, order);
        }
    }
}
