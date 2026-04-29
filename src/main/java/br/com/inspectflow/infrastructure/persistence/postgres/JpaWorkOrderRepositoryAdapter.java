package br.com.inspectflow.infrastructure.persistence.postgres;

import br.com.inspectflow.application.order.dto.SearchOrderFilterRequest;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import br.com.inspectflow.infrastructure.persistence.common.mappers.PaginationMapper;
import br.com.inspectflow.infrastructure.persistence.postgres.repositories.PostgresWorkOrderRepository;
import br.com.inspectflow.infrastructure.persistence.postgres.specifications.WorkOrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JpaWorkOrderRepositoryAdapter implements WorkOrderRepository {

    private final PostgresWorkOrderRepository repository;

    @Override
    public WorkOrder save(WorkOrder workOrder) {
        return repository.save(workOrder);
    }

    @Override
    public Optional<WorkOrder> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<WorkOrder> findAll() {
        return repository.findAll();
    }

    @Override
    public PagedResponse<WorkOrder> search(SearchOrderFilterRequest filter, PageRequest pageRequest) {

        Specification<WorkOrder> spec = WorkOrderSpecification.byFilter(filter);

        Pageable pageable = PaginationMapper.toPageable(pageRequest);

        Page<WorkOrder> page = repository.findAll(spec, pageable);

        return PaginationMapper.toPagedResponse(page);
    }

    @Override
    public List<WorkOrder> findAllByEquipmentCode(UUID equipmentCode) {
        return repository.findAllByEquipmentCode(equipmentCode);
    }

    @Override
    public PagedResponse<WorkOrder> findAll(PageRequest pageRequest) {
        Pageable pageable = PaginationMapper.toPageable(pageRequest);
        Page<WorkOrder> page = repository.findAll(pageable);
        return PaginationMapper.toPagedResponse(page);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<Object[]> countWorkOrdersByStatus() {
        return repository.countWorkOrdersByStatus();
    }

    @Override
    public List<Object[]> countWorkOrdersByStatusMonthly() {
        return repository.countWorkOrdersByStatusMonthly();
    }

    @Override
    public Double calculateAverageRepairTimeInHours() {
        return repository.calculateAverageRepairTimeInHours();
    }
}
