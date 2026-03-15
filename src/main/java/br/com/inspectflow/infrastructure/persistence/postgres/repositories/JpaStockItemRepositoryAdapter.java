package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.stock.models.StockItem;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.infrastructure.persistence.common.mappers.PaginationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaStockItemRepositoryAdapter implements StockItemRepository {

    private final PostgresStockItemRepository repository;

    @Override
    public StockItem save(StockItem stockItem) {
        return repository.save(stockItem);
    }

    @Override
    public Optional<StockItem> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<StockItem> findAll() {
        return repository.findAll();
    }

    @Override
    public PagedResponse<StockItem> findAll(PageRequest pageRequest) {
        Pageable pageable = PaginationMapper.toPageable(pageRequest);
        Page<StockItem> page = repository.findAll(pageable);
        return PaginationMapper.toPagedResponse(page);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsByNameOrSupplierCode(String name, String supplierCode) {
        return repository.existsByNameOrSupplierCode(name, supplierCode);
    }
}
