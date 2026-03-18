package br.com.inspectflow.application.http.handlers;

public class EquipmentNotFoundException extends RuntimeException{
    public EquipmentNotFoundException(String message) {
        super(message);
    }

    public EquipmentNotFoundException() {
        super("Equipamento não encontrado");
    }

}
