package br.com.inspectflow.domain.stock.repositories;

import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.domain.stock.models.StockItem;

import java.util.List;
import java.util.Optional;

public interface StockItemRepository {
    StockItem save(StockItem stockItem);
    Optional<StockItem> findById(Long id);
    List<StockItem> findAll();
    PagedResponse<StockItem> findAll(PageRequest pageRequest);
    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByName(String name);

    boolean existsBySupplierCode(String code);
}
