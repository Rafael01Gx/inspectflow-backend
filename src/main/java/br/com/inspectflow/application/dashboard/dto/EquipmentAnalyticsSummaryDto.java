package br.com.inspectflow.application.dashboard.dto;

import java.util.List;

public record EquipmentAnalyticsSummaryDto(
        List<TopEquipmentByOrdersDto> topByOrders,
        List<TopPartUsedDto> topPartsUsed,
        List<FailureTrendDto> failureTrend,
        List<EquipmentResolutionDto> resolutionRanking
) {
}
