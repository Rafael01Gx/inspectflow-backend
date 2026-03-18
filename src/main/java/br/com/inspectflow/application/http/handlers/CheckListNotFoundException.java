package br.com.inspectflow.application.http.handlers;

public class CheckListNotFoundException extends RuntimeException {
    public CheckListNotFoundException(String message) {
        super(message);
    }

    public CheckListNotFoundException() {
        super("Checklist não encontrado!");
    }
}
