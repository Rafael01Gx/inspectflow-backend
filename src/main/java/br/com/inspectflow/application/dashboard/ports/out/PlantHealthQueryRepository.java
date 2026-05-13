package br.com.inspectflow.application.dashboard.ports.out;

import br.com.inspectflow.application.dashboard.dto.CriticalStockDto;
import br.com.inspectflow.application.dashboard.dto.OpenOrderByPriorityDto;
import br.com.inspectflow.application.dashboard.dto.OverdueInspectionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlantHealthQueryRepository {

    long countEquipmentsWithOverdueInspection();

    long countEquipmentsWithCriticalOpenOrder();

    long countStockItemsBelowMinimum();

    long countTotalEquipments();

    Page<OverdueInspectionDto> findOverdueInspections(Pageable pageable);

    List<OpenOrderByPriorityDto> findOpenOrdersByPriority();

    List<CriticalStockDto> findCriticalStockItems();
}
