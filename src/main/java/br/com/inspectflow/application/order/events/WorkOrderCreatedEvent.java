package br.com.inspectflow.application.order.events;

import br.com.inspectflow.domain.order.enums.OrderPriority;
import br.com.inspectflow.domain.order.enums.OrderStatus;
import br.com.inspectflow.domain.order.models.WorkOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record WorkOrderCreatedEvent(
        UUID id,
        String title,
        String description,
        String equipmentName,
        String equipmentCode,
        OrderStatus orderStatus,
        OrderPriority orderPriority,
        LocalDate dueDate,
        String assignee,
        List<String> systemInfo,
        String stockRequisition,
        LocalDateTime createdAt


        ) {

    public static WorkOrderCreatedEvent from(WorkOrder order) {
        return new WorkOrderCreatedEvent(
                order.getId(),
                order.getTitle(),
                order.getDescription(),
                order.getEquipmentName(),
                order.getEquipment().getCode(),
                order.getOrderStatus(),
                order.getOrderPriority(),
                order.getDueDate(),
                order.getAssignee().getName(),
                order.getSystemInfo(),
                order.getStockRequisition(),
                order.getCreatedAt()
        );
    }
}
