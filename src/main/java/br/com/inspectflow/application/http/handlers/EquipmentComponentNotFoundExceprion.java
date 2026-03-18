package br.com.inspectflow.application.http.handlers;

public class EquipmentComponentNotFoundExceprion extends  RuntimeException {

    public EquipmentComponentNotFoundExceprion() {
        super("Componente não encontrado!");
    }

    public EquipmentComponentNotFoundExceprion(String message) {
        super(message);
    }

}
