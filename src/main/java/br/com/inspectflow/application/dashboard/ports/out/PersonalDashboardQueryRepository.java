package br.com.inspectflow.application.dashboard.ports.out;

import br.com.inspectflow.application.dashboard.dto.PersonalActivityDto;
import br.com.inspectflow.application.dashboard.dto.PersonalSummaryDto;
import br.com.inspectflow.application.dashboard.dto.PersonalWorkOrderSummaryDto;
import br.com.inspectflow.application.dashboard.dto.PersonalWorkOrderTimelineDto;

import java.util.List;
import java.util.UUID;

public interface PersonalDashboardQueryRepository {

    PersonalSummaryDto findPersonalSummary(UUID userId);

    List<PersonalActivityDto> findActivityByPeriod(UUID userId, String groupBy);

    List<PersonalWorkOrderTimelineDto> findWorkOrderTimeline(UUID userId, int months);

    List<PersonalWorkOrderSummaryDto> findPendingWorkOrders(UUID userId);

    List<PersonalWorkOrderSummaryDto> findRecentCompletedWorkOrders(UUID userId, int limit);
}
