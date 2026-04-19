package br.com.inspectflow.application.dashboard.dto;

import br.com.inspectflow.domain.order.enums.OrderStatus;

public record WorkOrderStatusCountDto(
    OrderStatus status,
    long count
) {}
