package br.com.inspectflow.application.equipment.validators;

public interface EquipmentValidator<T>{
    void execute(T validate);
}
