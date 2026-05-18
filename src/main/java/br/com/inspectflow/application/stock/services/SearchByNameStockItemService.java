package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.stock.dto.StockItemResponse;
import br.com.inspectflow.application.stock.ports.in.SearchByNameStockItemUseCase;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchByNameStockItemService implements SearchByNameStockItemUseCase {
    private final StockItemRepository repository;

    @Override
    @Observed(name = "stock.find-name",
            contextualName = "busca item de estoque por name")
    public List<StockItemResponse> execute(String name) {
        return repository.findTop10ByNameContainingIgnoreCase(name).stream().map(StockItemResponse::from).toList();
    }
}
