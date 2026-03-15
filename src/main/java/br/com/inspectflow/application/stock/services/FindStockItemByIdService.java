package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.http.handlers.StockItemNotFoundException;
import br.com.inspectflow.application.stock.dto.StockItemResponse;
import br.com.inspectflow.application.stock.ports.in.FindStockItemByIdUseCase;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindStockItemByIdService implements FindStockItemByIdUseCase {
    private final StockItemRepository repository;

    @Override
    public StockItemResponse execute(Long id) {
        return StockItemResponse
                .from(repository.findById(id)
                        .orElseThrow(StockItemNotFoundException::new));
    }
}
