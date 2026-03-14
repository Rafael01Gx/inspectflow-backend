package br.com.inspectflow.domain.stock.repositories;

import br.com.inspectflow.domain.stock.models.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockItemRepository extends JpaRepository<StockItem, Long> {
}
