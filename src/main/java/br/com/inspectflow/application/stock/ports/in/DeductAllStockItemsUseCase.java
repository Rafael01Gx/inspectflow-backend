package br.com.inspectflow.application.stock.ports.in;

import br.com.inspectflow.application.stock.dto.DeductStockRequest;

import java.util.List;

public interface DeductAllStockItemsUseCase {

    void execute(List<DeductStockRequest> request);
}
