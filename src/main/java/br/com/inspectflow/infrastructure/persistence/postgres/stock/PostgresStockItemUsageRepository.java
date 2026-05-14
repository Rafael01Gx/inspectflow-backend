package br.com.inspectflow.infrastructure.persistence.postgres.stock;

import br.com.inspectflow.domain.stock.models.StockItemUsage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostgresStockItemUsageRepository extends JpaRepository<StockItemUsage, UUID> {
    List<StockItemUsage> findByWorkOrderId(UUID workOrderId);

    Page<StockItemUsage> findAllByStockItemId(Long stockItemId, Pageable pageable);
}
