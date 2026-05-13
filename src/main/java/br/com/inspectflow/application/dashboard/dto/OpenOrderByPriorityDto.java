package br.com.inspectflow.application.dashboard.dto;

public record OpenOrderByPriorityDto(
        String equipmentName,
        String priority,
        long total,
        String earliestDueDate
) {
}
