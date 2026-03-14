package br.com.inspectflow.adapters.in.web.order.dto;

import br.com.inspectflow.domain.order.models.MaintenancePart;

import java.time.LocalDate;
import java.util.Set;

public record OrderResponse(
        String id,
        String title,
        String description,
        String equipmentName,
        String equipmentId,
        String orderStatus,
        String orderPriority,
        LocalDate dueDate,
        String assignee,
        Set<MaintenancePart> parts,
        String performedWork,
        LocalDate completionDate
) {
}
