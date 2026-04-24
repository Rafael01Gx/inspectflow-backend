package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.stock.dto.StockItemResponse;
import br.com.inspectflow.application.stock.ports.in.FindAllStockItemsUseCase;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllStockItemsService implements FindAllStockItemsUseCase {

    private final StockItemRepository repository;

    @Override
    public PagedResponse<StockItemResponse> execute(PageRequest pageRequest) {
        var page =repository.findAll(pageRequest);
        return new  PagedResponse<StockItemResponse>(
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
    public List<StockItemResponse> execute() {
        return repository.findAll().stream().map(StockItemResponse::from)
                .toList();
    }
}
