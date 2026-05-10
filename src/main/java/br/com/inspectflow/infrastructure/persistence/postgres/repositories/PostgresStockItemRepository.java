package br.com.inspectflow.infrastructure.persistence.postgres.repositories;

import br.com.inspectflow.domain.stock.models.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostgresStockItemRepository extends JpaRepository<StockItem, Long> {
    boolean existsBySupplierCode(String supplierCode);

    boolean existsByName(String name);

    List<StockItem> findAllByNameStartingWithIgnoreCase(String name);

    List<StockItem> findTop5ByNameContainingIgnoreCase(String name);

    @Query("SELECT s FROM StockItem s WHERE s.quantity <= s.minQuantity")
    List<StockItem> findByLowQuantity();


    @Query(value = """
        SELECT *
        FROM stock_items s
        WHERE similarity(
            immutable_unaccent(lower(s.name)), 
            immutable_unaccent(lower(:name))
        ) > 0.1
        ORDER BY similarity(
            immutable_unaccent(lower(s.name)), 
            immutable_unaccent(lower(:name))
        ) DESC
        LIMIT 5
        """, nativeQuery = true)
    List<StockItem> searchSmart(@Param("name") String name);
}
