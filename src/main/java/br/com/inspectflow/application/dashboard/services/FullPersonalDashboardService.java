package br.com.inspectflow.application.dashboard.services;

import br.com.inspectflow.application.dashboard.dto.PersonalDashboardFullDto;
import br.com.inspectflow.application.dashboard.ports.in.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FullPersonalDashboardService implements FullPersonalDashboardUseCase {

    private static final int DEFAULT_RECENT_COMPLETED_LIMIT = 10;
    private final FindWorkOrderTimelineByUserUseCase findWorkOrderTimeline;
    private final FindRecentCompletedWorkOrdersByUserUseCase findRecentCompletedWorkOrders;
    private final FindPendingWorkOrdersByUserUseCase findPendingWorkOrders;
    private final PersonalSummaryUseCase personalSummary;
    private final PersonalActivityUseCase personalActivity;

    @Override
    public PersonalDashboardFullDto execute(UUID userId, int months) {
        return new PersonalDashboardFullDto(
                personalSummary.execute(userId),
                personalActivity.execute(userId, "day"),
                findWorkOrderTimeline.execute(userId, months),
                findPendingWorkOrders.execute(userId),
                findRecentCompletedWorkOrders.execute(userId, DEFAULT_RECENT_COMPLETED_LIMIT)
        );
    }
}
