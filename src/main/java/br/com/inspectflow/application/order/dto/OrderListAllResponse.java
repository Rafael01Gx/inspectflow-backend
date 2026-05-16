package br.com.inspectflow.application.order.dto;

import br.com.inspectflow.domain.order.enums.OrderPriority;
import br.com.inspectflow.domain.order.enums.OrderStatus;
import br.com.inspectflow.domain.order.models.WorkOrder;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record OrderListAllResponse(
        String id,
        String title,
        String description,
        String equipmentName,
        OrderStatus orderStatus,
        OrderPriority orderPriority,
        LocalDate dueDate,
        String assignee,
        String performedWork,
        LocalDateTime completionDate,
        LocalDateTime createdAt
) {

    public static OrderListAllResponse from(WorkOrder order) {
        return  OrderListAllResponse.builder()
                .id(order.getId().toString())
                .title(order.getTitle())
                .description(order.getDescription())
                .equipmentName(order.getEquipmentName())
                .orderStatus(order.getOrderStatus())
                .orderPriority(order.getOrderPriority())
                .dueDate(order.getDueDate())
                .assignee(order.getAssignee().getName() + " - " + order.getAssignee().getRole().name())
                .performedWork(order.getPerformedWork())
                .completionDate(order.getCompletionDate())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
