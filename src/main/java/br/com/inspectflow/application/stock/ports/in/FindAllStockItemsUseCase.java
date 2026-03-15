package br.com.inspectflow.application.stock.ports.in;

import br.com.inspectflow.application.stock.dto.StockItemResponse;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;

public interface FindAllStockItemsUseCase {

    PagedResponse<StockItemResponse> execute(PageRequest pageRequest);

}
