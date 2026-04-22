package br.com.inspectflow.adapters.in.web.dashboard.controller;

import br.com.inspectflow.application.dashboard.dto.*;
import br.com.inspectflow.application.dashboard.ports.in.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@PreAuthorize("hasRole('GESTOR')")
@RequiredArgsConstructor
public class DashboardController {

    private final EquipmentStatusCountUseCase equipmentStatusCount;
    private final InspectionSummaryUseCase inspectionSummary;
    private final KpiSummaryUseCase kpiSummary;
    private final StockLowQuantityUseCase stockLowQuantity;
    private final WorkOrderStatusCountUseCase workOrderStatusCount;
    private final WorkOrderStatusMonthlyCountUseCase workOrderStatusMonthlyCount;

    @GetMapping("/inspections/summary")
    public ResponseEntity<InspectionSummaryDto> getInspectionSummary() {
        return ResponseEntity.ok(inspectionSummary.execute());
    }

    @GetMapping("/equipments/status-counts")
    public ResponseEntity<List<EquipmentStatusCountDto>> getEquipmentStatusCounts() {
        return ResponseEntity.ok(equipmentStatusCount.execute());
    }

    @GetMapping("/stock-items/low-quantity")
    public ResponseEntity<List<StockLowQuantityDto>> getLowQuantityStockItems() {
        return ResponseEntity.ok(stockLowQuantity.execute());
    }

    @GetMapping("/work-orders/status-counts")
    public ResponseEntity<List<WorkOrderStatusCountDto>> getWorkOrderStatusCounts() {
        return ResponseEntity.ok(workOrderStatusCount.execute());
    }

    @GetMapping("/work-orders/monthly-status-counts")
    public ResponseEntity<List<WorkOrderStatusMonthlyCountDto>> getMonthlyWorkOrderStatusCounts() {
        return ResponseEntity.ok(workOrderStatusMonthlyCount.execute());
    }

    @GetMapping("/kpis/summary")
    public ResponseEntity<KpiSummaryDto> getKpiSummary() {
        return ResponseEntity.ok(kpiSummary.execute());
    }
}
