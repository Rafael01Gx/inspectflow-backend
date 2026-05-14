package br.com.inspectflow.application.dashboard.dto;

public record OpenOrderByPriorityDto(
        String equipmentName,
        String priority,
        Long total,
        String earliestDueDate
) {
}
