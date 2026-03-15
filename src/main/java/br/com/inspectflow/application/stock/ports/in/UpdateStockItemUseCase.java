package br.com.inspectflow.application.stock.ports.in;

import br.com.inspectflow.application.stock.dto.StockItemResponse;
import br.com.inspectflow.application.stock.dto.UpdateStockItemRequest;

public interface UpdateStockItemUseCase {

    StockItemResponse execute(Long id, UpdateStockItemRequest dto);
}
