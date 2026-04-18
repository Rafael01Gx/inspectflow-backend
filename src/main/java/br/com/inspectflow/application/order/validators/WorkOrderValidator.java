package br.com.inspectflow.application.order.validators;

public interface WorkOrderValidator<T> {

    void execute(T validate);
}
