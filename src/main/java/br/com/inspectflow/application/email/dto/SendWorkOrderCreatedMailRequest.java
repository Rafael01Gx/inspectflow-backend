package br.com.inspectflow.application.email.dto;

import br.com.inspectflow.application.order.events.WorkOrderCreatedEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record SendWorkOrderCreatedMailRequest(
        UUID orderId,
        String equipmentName,
        String codigoEquipamento,
        String orderPriority,
        LocalDate dueDate,
        String title,
        String description,
        String stockRequisition,
        String assignee,
        LocalDateTime createdAt
) {

    public static SendWorkOrderCreatedMailRequest from( WorkOrderCreatedEvent order){
        return new SendWorkOrderCreatedMailRequest(
                order.id(),
                order.equipmentName(),
                order.equipmentCode(),
                order.orderPriority().getValue(),
                order.dueDate(),
                order.title(),
                order.description(),
                order.stockRequisition(),
                order.assignee(),
                order.createdAt()
        );
    }
}
