package br.com.inspectflow.application.dashboard.services;

import br.com.inspectflow.application.dashboard.dto.KpiSummaryDto;
import br.com.inspectflow.application.dashboard.ports.in.KpiSummaryUseCase;
import br.com.inspectflow.domain.inspection.repositories.InspectionRepository;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KpiSummaryService implements KpiSummaryUseCase {
    private final InspectionRepository inspectionRepository;
    private final WorkOrderRepository workOrderRepository;

    @Override
    @Cacheable(value = "dashboardKpis", key = "'summary'")
    public KpiSummaryDto execute() {
        Double mttrInHours = workOrderRepository.calculateAverageRepairTimeInHours();
        if (mttrInHours == null) {
            mttrInHours = 0.0;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fifteenDaysLater = now.plusDays(15);
        long upcomingInspectionsCount = inspectionRepository.countByDateBetweenAndStatusNotIn(now, fifteenDaysLater);

        long completedAndOnTime = inspectionRepository.countCompletedAndOnTimeInspections(now);
        long allInspectionsUpToNow = inspectionRepository.countAllInspectionsUpTo(now);
        double complianceRate = 0.0;
        if (allInspectionsUpToNow > 0) {
            complianceRate = (double) completedAndOnTime / allInspectionsUpToNow * 100.0;
        }

        return new KpiSummaryDto(mttrInHours, upcomingInspectionsCount, complianceRate);
    }
}
