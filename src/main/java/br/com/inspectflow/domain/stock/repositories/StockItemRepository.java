package br.com.inspectflow.domain.stock.repositories;

import br.com.inspectflow.domain.stock.models.StockItem;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Optional;
import java.util.List;

public interface StockItemRepository {
    StockItem save(StockItem stockItem);
    Optional<StockItem> findById(Long id);
    List<StockItem> findAll();
    PagedResponse<StockItem> findAll(PageRequest pageRequest);
    void deleteById(Long id);

    boolean existsByNameOrSupplierCode(@NotBlank @Size(min = 3, max = 50) String name, @Size(min = 3, max = 50) String supplierCode);
}
