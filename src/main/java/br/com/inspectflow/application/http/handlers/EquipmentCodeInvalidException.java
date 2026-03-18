package br.com.inspectflow.application.http.handlers;

public class EquipmentCodeInvalidException extends RuntimeException {
    public EquipmentCodeInvalidException(String message) {
        super(message);
    }

    public EquipmentCodeInvalidException(){
        super("Código de equipamento inválido!");
    }
}
