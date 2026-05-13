package br.com.inspectflow.application.dashboard.dto;

public record PersonalWorkOrderSummaryDto(
        String workOrderId,
        String title,
        String equipmentName,
        String status,
        String priority,
        String dueDate,
        String completionDate
) {
}
