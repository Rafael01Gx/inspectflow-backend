package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.stock.ports.in.CreateStockItemUsageUseCase;
import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.stock.models.StockItem;
import br.com.inspectflow.domain.stock.models.StockItemUsage;
import br.com.inspectflow.domain.stock.repositories.StockItemUsageRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateStockItemUsageService implements CreateStockItemUsageUseCase {
    private final StockItemUsageRepository repository;

    @Override
    @Transactional
    @Observed(name = "stock.history-create",
            contextualName = "adiciona uso do stockItem ao histórico")
    @CacheEvict(value = "stockItemUsage", allEntries = true)
    public StockItemUsage execute(WorkOrder workOrder, StockItem stockItem, Integer quantityUsed) {

        StockItemUsage usage = StockItemUsage.builder()
                .stockItem(stockItem)
                .workOrder(workOrder)
                .quantityUsed(quantityUsed)
                .build();

        repository.save(usage);

        return usage;
    }
}
