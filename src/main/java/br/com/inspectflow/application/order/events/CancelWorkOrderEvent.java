package br.com.inspectflow.application.order.events;

import br.com.inspectflow.domain.order.models.WorkOrder;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record CancelWorkOrderEvent(
        UUID id,
        String title,
        String equipmentName,
        String orderStatus,
        String assignee,
        String performedWork,
        LocalDateTime completionDate
) {
    public static CancelWorkOrderEvent from(WorkOrder order){
        return new CancelWorkOrderEvent(
                order.getId(),
                order.getTitle(),
                order.getEquipmentName(),
                order.getOrderStatus().getValue(),
                order.getAssignee().getName() + " - " + order.getAssignee().getRole().name(),
                order.getPerformedWork(),
                order.getCompletionDate()
        );
    }
}
