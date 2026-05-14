package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.stock.dto.StockItemUsageResponse;
import br.com.inspectflow.application.stock.ports.in.FindAllStockItemUsageUseCase;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.domain.stock.models.StockItemUsage;
import br.com.inspectflow.domain.stock.repositories.StockItemUsageRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindAllStockItemUsageService implements FindAllStockItemUsageUseCase {
    private final StockItemUsageRepository repository;

    @Override
    @Transactional(readOnly = true)
    @Observed(name = "stock.history-list",
            contextualName = "lista histórico de uso de um StockItem")
    @Cacheable(value = "stockItemUsage", key = "'id:' + #id + ':p:' + #pageRequest.page + ':s:' + #pageRequest.size")
    public PagedResponse<StockItemUsageResponse> execute(Long id,PageRequest pageRequest) {

        PagedResponse<StockItemUsage> page = repository.findAllByStockItemId(id,pageRequest);

        return new PagedResponse<StockItemUsageResponse>(
                page.content().stream()
                        .map(StockItemUsageResponse::from)
                        .toList(),
                page.pageNumber(),
                page.pageSize(),
                page.totalElements(),
                page.totalPages(),
                page.isLast()
        );
    }
}
