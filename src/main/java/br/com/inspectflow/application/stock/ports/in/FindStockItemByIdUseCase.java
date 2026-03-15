package br.com.inspectflow.application.stock.ports.in;

import br.com.inspectflow.application.stock.dto.StockItemResponse;

public interface FindStockItemByIdUseCase {

    StockItemResponse execute(Long id);

}
