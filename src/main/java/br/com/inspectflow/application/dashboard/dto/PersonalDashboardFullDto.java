package br.com.inspectflow.application.dashboard.dto;

import java.util.List;

public record PersonalDashboardFullDto(
        PersonalSummaryDto summary,
        PersonalActivityResponse activityTimeline,
        List<PersonalWorkOrderTimelineDto> workOrderTimeline,
        List<PersonalWorkOrderSummaryDto> pendingWorkOrders,
        List<PersonalWorkOrderSummaryDto> recentCompleted
) {
}
