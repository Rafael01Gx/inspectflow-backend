package br.com.inspectflow.domain.order.repositories;

import br.com.inspectflow.application.order.dto.SearchOrderFilterRequest;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.user.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkOrderRepository {
    WorkOrder save(WorkOrder workOrder);
    Optional<WorkOrder> findById(UUID id);
    List<WorkOrder> findAll();
    PagedResponse<WorkOrder> search(SearchOrderFilterRequest search, PageRequest pageRequest);
    List<WorkOrder> findAllByEquipmentCode(UUID equipmentCode);
    PagedResponse<WorkOrder> findAll(PageRequest pageRequest);
    void deleteById(UUID id);
    List<Object[]> countWorkOrdersByStatus();
    List<Object[]> countWorkOrdersByStatusMonthly();
    Double calculateAverageRepairTimeInHours();
    PagedResponse<WorkOrder> findAllByAssignee(User assignee, PageRequest pageRequest);
}
