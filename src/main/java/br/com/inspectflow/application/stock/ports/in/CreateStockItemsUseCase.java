package br.com.inspectflow.application.stock.ports.in;

import br.com.inspectflow.application.stock.dto.CreateStockItemRequest;
import br.com.inspectflow.application.stock.dto.StockItemResponse;

public interface CreateStockItemsUseCase {

    StockItemResponse execute(CreateStockItemRequest dto);
}
