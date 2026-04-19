package br.com.inspectflow.application.stock.ports.in;

import br.com.inspectflow.application.stock.dto.StockItemUsageResponse;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;

public interface FindAllStockItemUsageUseCase {

    PagedResponse<StockItemUsageResponse> execute(Long id,PageRequest pageRequest);
}
