package br.com.inspectflow.application.dashboard.services;

import br.com.inspectflow.application.dashboard.dto.PlantHealthOverviewDto;
import br.com.inspectflow.application.dashboard.ports.in.PlantHealthOverviewUseCase;
import br.com.inspectflow.application.dashboard.ports.out.PlantHealthQueryRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlantHealthOverviewService implements PlantHealthOverviewUseCase {
    private final PlantHealthQueryRepository repository;
    private static final double WEIGHT_INSPECTION = 0.5;
    private static final double WEIGHT_ORDERS     = 0.3;
    private static final double WEIGHT_STOCK      = 0.2;

    @Override
    @Cacheable(value = "plantHealthOverview", key = "'overview'")
    @Transactional(readOnly = true)
    @Observed(name = "dashboard.plant-health.overview", contextualName = "plant health overview")
    public PlantHealthOverviewDto execute() {
        long totalEquipments                  = repository.countTotalEquipments();
        long equipmentsWithOverdueInspection  = repository.countEquipmentsWithOverdueInspection();
        long equipmentsWithCriticalOpenOrder  = repository.countEquipmentsWithCriticalOpenOrder();
        long stockItemsBelowMinimum           = repository.countStockItemsBelowMinimum();

        double healthScore = calculateHealthScore(
                totalEquipments,
                equipmentsWithOverdueInspection,
                equipmentsWithCriticalOpenOrder,
                stockItemsBelowMinimum
        );

        return new PlantHealthOverviewDto(
                totalEquipments,
                equipmentsWithOverdueInspection,
                equipmentsWithCriticalOpenOrder,
                stockItemsBelowMinimum,
                healthScore
        );
    }

    private double calculateHealthScore(
            long total,
            long overdueInspections,
            long criticalOrders,
            long lowStock
    ) {
        if (total == 0) return 100.0;

        double inspectionPenalty = (double) overdueInspections / total * 100.0 * WEIGHT_INSPECTION;
        double orderPenalty      = (double) criticalOrders      / total * 100.0 * WEIGHT_ORDERS;
        double stockPenalty      = (double) lowStock            / total * 100.0 * WEIGHT_STOCK;

        double score = 100.0 - inspectionPenalty - orderPenalty - stockPenalty;
        return Math.max(0.0, Math.round(score * 10.0) / 10.0);
    }
}
