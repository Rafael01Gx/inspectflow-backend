package br.com.inspectflow.application.stock.ports.in;

public interface DeductStockItemUseCase {

    void execute(Long id, Integer quantity);

}
