package br.com.inspectflow.application.dashboard.ports.out;

import br.com.inspectflow.application.dashboard.dto.EquipmentResolutionDto;
import br.com.inspectflow.application.dashboard.dto.FailureTrendDto;
import br.com.inspectflow.application.dashboard.dto.TopEquipmentByOrdersDto;
import br.com.inspectflow.application.dashboard.dto.TopPartUsedDto;

import java.util.List;

public interface EquipmentAnalyticsQueryRepository {

    List<TopEquipmentByOrdersDto> findTopEquipmentsByOrders(int limit);

    List<TopPartUsedDto> findTopPartsUsed(int limit);

    List<FailureTrendDto> findFailureTrend(int months);

    List<EquipmentResolutionDto> findEquipmentResolutionRanking(int limit);
}
