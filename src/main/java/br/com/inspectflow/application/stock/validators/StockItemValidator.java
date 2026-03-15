package br.com.inspectflow.application.stock.validators;

public interface StockItemValidator<T> {
    void execute(T validate);
}
