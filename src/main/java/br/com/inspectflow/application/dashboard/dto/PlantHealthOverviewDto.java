package br.com.inspectflow.application.dashboard.dto;

public record PlantHealthOverviewDto(
        long totalEquipments,
        long equipmentsWithOverdueInspection,
        long equipmentsWithCriticalOpenOrder,
        long stockItemsBelowMinimum,
        double overallHealthScore
) {
}
