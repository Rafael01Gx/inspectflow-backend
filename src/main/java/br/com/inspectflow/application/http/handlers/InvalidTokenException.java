package br.com.inspectflow.application.http.handlers;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("Token inválido");
    }

}
