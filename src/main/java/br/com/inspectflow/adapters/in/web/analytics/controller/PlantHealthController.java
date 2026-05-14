package br.com.inspectflow.adapters.in.web.analytics.controller;

import br.com.inspectflow.application.dashboard.dto.CriticalStockDto;
import br.com.inspectflow.application.dashboard.dto.OpenOrderByPriorityDto;
import br.com.inspectflow.application.dashboard.dto.OverdueInspectionDto;
import br.com.inspectflow.application.dashboard.dto.PlantHealthOverviewDto;
import br.com.inspectflow.application.dashboard.ports.in.CriticalStockUseCase;
import br.com.inspectflow.application.dashboard.ports.in.OpenOrderByPriorityUseCase;
import br.com.inspectflow.application.dashboard.ports.in.PlantHealthOverviewUseCase;
import br.com.inspectflow.application.dashboard.ports.in.PlantOverdueInspectionUseCase;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/dashboard/plant-health")
@PreAuthorize("hasRole('SUPERVISOR')")
@RequiredArgsConstructor
public class PlantHealthController {
    private final PlantHealthOverviewUseCase plantHealthOverview;
    private final CriticalStockUseCase criticalStock;
    private final OpenOrderByPriorityUseCase openOrderByPriority;
    private final PlantOverdueInspectionUseCase overdueInspection;


    @GetMapping("/overview")
    public ResponseEntity<PlantHealthOverviewDto> getOverview() {
        return ResponseEntity.ok(plantHealthOverview.execute());
    }

    @GetMapping("/overdue-inspections")
    public ResponseEntity<PagedResponse<OverdueInspectionDto>> getOverdueInspections(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(overdueInspection.execute(pageable));
    }

    @GetMapping("/open-orders")
    public ResponseEntity<List<OpenOrderByPriorityDto>> getOpenOrdersByPriority() {
        return ResponseEntity.ok(openOrderByPriority.execute());
    }

    @GetMapping("/critical-stock")
    public ResponseEntity<List<CriticalStockDto>> getCriticalStock() {
        return ResponseEntity.ok(criticalStock.execute());
    }
}
