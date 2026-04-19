package br.com.inspectflow.application.stock.ports.in;

import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.stock.models.StockItem;
import br.com.inspectflow.domain.stock.models.StockItemUsage;

public interface CreateStockItemUsageUseCase {

    StockItemUsage execute(WorkOrder workOrder, StockItem stockItem, Integer quantityUsed);
}
