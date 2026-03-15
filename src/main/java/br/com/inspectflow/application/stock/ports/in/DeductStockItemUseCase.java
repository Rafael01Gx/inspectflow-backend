package br.com.inspectflow.application.stock.ports.in;

import br.com.inspectflow.application.stock.dto.DeductStockRequest;

public interface DeductStockItemUseCase {

    void execute(Long id, DeductStockRequest request);

}
