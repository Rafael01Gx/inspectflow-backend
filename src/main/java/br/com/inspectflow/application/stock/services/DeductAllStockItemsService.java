package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.http.handlers.StockItemNotFoundException;
import br.com.inspectflow.application.stock.dto.DeductStockRequest;
import br.com.inspectflow.application.stock.ports.in.DeductAllStockItemsUseCase;
import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.stock.models.StockItem;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import io.micrometer.observation.annotation.Observed;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeductAllStockItemsService implements DeductAllStockItemsUseCase {
    private final StockItemRepository repository;
    private final CreateStockItemUsageService createStockItemUsage;

    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = "stockItemUsage", allEntries = true),
            @CacheEvict(value = "dashboardStockItems", key = "'lowQuantity'")
    })
    @Observed(name = "stock.deduct",
            contextualName = "reduz item no estoque")
    public void execute(List<DeductStockRequest> request, WorkOrder workOrder) {
        for (DeductStockRequest dto : request) {
            StockItem item = repository.findById(dto.id()).orElseThrow(StockItemNotFoundException::new);

            if (item.getQuantity() < dto.quantity())
                throw new IllegalArgumentException("Quantidade insuficiente em estoque: " + item.getName().toUpperCase() + " - " + dto.quantity() + " unidades   ");
            item.deductStock(dto.quantity());
            createStockItemUsage.execute(workOrder, item, dto.quantity());
            repository.save(item);
        }
    }
}
