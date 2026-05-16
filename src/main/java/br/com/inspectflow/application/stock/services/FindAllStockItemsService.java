package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.stock.dto.StockItemResponse;
import br.com.inspectflow.application.stock.dto.StockListAllResponse;
import br.com.inspectflow.application.stock.ports.in.FindAllStockItemsUseCase;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindAllStockItemsService implements FindAllStockItemsUseCase {

    private final StockItemRepository repository;

    @Override
    @Observed(name = "stock.list",
            contextualName = "lista itens de estoque")
    @Cacheable(value = "allStockItem", key = "':p:' + #pageRequest.page + ':s:' + #pageRequest.size")
    @Transactional(readOnly = true)
    public PagedResponse<StockItemResponse> execute(PageRequest pageRequest) {
        var page = repository.findAll(pageRequest);
        return new PagedResponse<StockItemResponse>(
                page.content().stream()
                        .map(StockItemResponse::from)
                        .toList(),
                page.pageNumber(),
                page.pageSize(),
                page.totalElements(),
                page.totalPages(),
                page.isLast()
        );
    }

    @Override
    @Cacheable(value = "allStockItem", key = "'list'")
    @Transactional(readOnly = true)
    public List<StockListAllResponse> execute() {
        return repository.findAll().stream().map(StockListAllResponse::from)
                .toList();
    }
}
