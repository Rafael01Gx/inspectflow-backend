package br.com.inspectflow.application.dashboard.dto;

public record FailureTrendDto(
        String month,
        String equipmentName,
        String priority,
        long total
) {
}
