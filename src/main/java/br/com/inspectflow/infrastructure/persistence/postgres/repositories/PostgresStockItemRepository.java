package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.stock.models.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostgresStockItemRepository extends JpaRepository<StockItem, Long> {
    boolean existsBySupplierCode(String supplierCode);

    boolean existsByName(String name);

    List<StockItem> findAllByNameStartingWithIgnoreCase(String name);

    List<StockItem> findTop5ByNameContainingIgnoreCase(String name);

    @Query("SELECT s FROM StockItem s WHERE s.quantity <= s.minQuantity")
    List<StockItem> findByLowQuantity();
}
