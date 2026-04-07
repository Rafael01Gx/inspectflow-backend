package br.com.inspectflow.application.order.dto;

import br.com.inspectflow.domain.order.models.MaintenancePart;

import java.time.LocalDate;
import java.util.List;

public record CompleteOrderRequest(
        String performedWork,
        List<MaintenancePart> parts,
        boolean reschedule,
        LocalDate nextDate
) {
}
