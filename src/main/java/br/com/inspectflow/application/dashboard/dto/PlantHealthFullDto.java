package br.com.inspectflow.application.dashboard.dto;

import java.util.List;

public record PlantHealthFullDto(
        PlantHealthOverviewDto overview,
        List<OverdueInspectionDto> overdueInspections,
        List<OpenOrderByPriorityDto> openOrdersByPriority,
        List<CriticalStockDto> criticalStock
) {
}
