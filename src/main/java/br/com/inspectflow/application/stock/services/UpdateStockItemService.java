package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.stock.dto.StockItemResponse;
import br.com.inspectflow.application.stock.dto.UpdateStockItemRequest;
import br.com.inspectflow.application.stock.ports.in.UpdateStockItemUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateStockItemService implements UpdateStockItemUseCase {
    @Override
    public StockItemResponse execute(Long id, UpdateStockItemRequest dto) {
        return null;
    }
}
