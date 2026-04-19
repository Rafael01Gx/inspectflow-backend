package br.com.inspectflow.domain.stock.repositories;

import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.domain.stock.models.StockItemUsage;

import java.util.List;
import java.util.UUID;

public interface StockItemUsageRepository {
    List<StockItemUsage> findAll();

    PagedResponse<StockItemUsage> findAll(PageRequest pageRequest);
    List<StockItemUsage> findByWorkOrderId(UUID workOrderId);
    PagedResponse<StockItemUsage> findAllByStockItemId(Long stockItemId, PageRequest pageRequest);
    StockItemUsage save(StockItemUsage stockItemUsage);

}
