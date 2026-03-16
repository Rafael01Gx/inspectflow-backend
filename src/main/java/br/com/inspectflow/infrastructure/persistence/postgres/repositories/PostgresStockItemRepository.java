package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.stock.models.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgresStockItemRepository extends JpaRepository<StockItem, Long> {
    boolean existsBySupplierCode(String supplierCode);

    boolean existsByName(String name);
}
