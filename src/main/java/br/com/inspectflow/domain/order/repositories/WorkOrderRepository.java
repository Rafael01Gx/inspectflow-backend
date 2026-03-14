package br.com.inspectflow.domain.order.repositories;

import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface WorkOrderRepository {
    WorkOrder save(WorkOrder workOrder);
    Optional<WorkOrder> findById(UUID id);
    List<WorkOrder> findAll();
    PagedResponse<WorkOrder> findAll(PageRequest pageRequest);
    void deleteById(UUID id);
}
