package br.com.inspectflow.domain.estoque.repository;

import br.com.inspectflow.domain.estoque.models.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockItemRepository extends JpaRepository<StockItem, Long> {
}
