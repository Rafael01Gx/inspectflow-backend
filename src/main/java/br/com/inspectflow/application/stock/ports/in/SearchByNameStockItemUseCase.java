package br.com.inspectflow.application.stock.ports.in;

import br.com.inspectflow.application.stock.dto.StockItemResponse;

import java.util.List;

public interface SearchByNameStockItemUseCase {

    List<StockItemResponse> execute(String name);

}
