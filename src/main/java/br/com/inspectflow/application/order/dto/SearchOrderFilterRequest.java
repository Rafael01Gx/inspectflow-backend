package br.com.inspectflow.application.order.dto;

import br.com.inspectflow.domain.order.enums.OrderPriority;
import br.com.inspectflow.domain.order.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchOrderFilterRequest(
        String equipmentName,
        OrderStatus orderStatus,
        OrderPriority orderPriority,
        String assignee,
        LocalDateTime completionDate,
        LocalDateTime createdAt
) {
}
