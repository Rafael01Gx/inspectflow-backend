package br.com.inspectflow.application.handlers;

public class EmailAlreadyRegisteredException extends RuntimeException {

    public EmailAlreadyRegisteredException() {
        super("O e-mail informado já está cadastrado no sistema.");
    }

    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }

}
