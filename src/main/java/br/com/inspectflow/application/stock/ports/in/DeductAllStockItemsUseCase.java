package br.com.inspectflow.application.stock.ports.in;

import br.com.inspectflow.application.stock.dto.DeductStockRequest;
import br.com.inspectflow.domain.order.models.WorkOrder;

import java.util.List;

public interface DeductAllStockItemsUseCase {

    void execute(List<DeductStockRequest> request, WorkOrder workOrder);
}
