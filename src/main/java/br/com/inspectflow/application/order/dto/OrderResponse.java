package br.com.inspectflow.application.order.dto;

import br.com.inspectflow.domain.order.enums.OrderPriority;
import br.com.inspectflow.domain.order.enums.OrderStatus;
import br.com.inspectflow.domain.order.models.MaintenancePart;
import br.com.inspectflow.domain.order.models.WorkOrder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record OrderResponse(
        String id,
        String title,
        String description,
        String equipmentName,
        UUID equipmentId,
        OrderStatus orderStatus,
        OrderPriority orderPriority,
        LocalDate dueDate,
        String assignee,
        Set<MaintenancePart> parts,
        List<String> systemInfo,
        String performedWork,
        LocalDate completionDate
) {
    public static OrderResponse from(WorkOrder order) {
        return new OrderResponse(
                order.getId().toString(),
                order.getTitle(),
                order.getDescription(),
                order.getEquipmentName(),
                order.getEquipment().getId(),
                order.getOrderStatus(),
                order.getOrderPriority(),
                order.getDueDate(),
                order.getAssignee().getName(),
                order.getParts(),
                order.getSystemInfo(),
                order.getPerformedWork(),
                order.getCompletionDate()

        );
    }
}
