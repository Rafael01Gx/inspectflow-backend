package br.com.inspectflow.infrastructure.persistence.postgres.stock;

import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.domain.stock.models.StockItemUsage;
import br.com.inspectflow.domain.stock.repositories.StockItemUsageRepository;
import br.com.inspectflow.infrastructure.persistence.common.mappers.PaginationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JpaStockItemUsageRepositoryAdapter implements StockItemUsageRepository {
    private final PostgresStockItemUsageRepository repository;

    @Override
    public List<StockItemUsage> findAll() {
        return repository.findAll();
    }

    @Override
    public PagedResponse<StockItemUsage> findAll(PageRequest pageRequest) {
        Pageable pageable = PaginationMapper.toPageable(pageRequest);
        Page<StockItemUsage> page = repository.findAll(pageable);

        return PaginationMapper.toPagedResponse(page);
    }

    @Override
    public List<StockItemUsage> findByWorkOrderId(UUID workOrderId) {
        return repository.findByWorkOrderId(workOrderId);
    }

    @Override
    public PagedResponse<StockItemUsage> findAllByStockItemId(Long stockItemId, PageRequest pageRequest) {
        Pageable pageable = PaginationMapper.toPageable(pageRequest);
        Page<StockItemUsage> page = repository.findAllByStockItemId(stockItemId,pageable);

        return PaginationMapper.toPagedResponse(page);
    }

    @Override
    public StockItemUsage save(StockItemUsage stockItemUsage) {
        return repository.save(stockItemUsage);
    }
}
