package br.com.inspectflow.application.http.handlers;

public class IdMismatchException extends RuntimeException{

    public IdMismatchException(String message) {
        super(message);
    }

    public IdMismatchException() {
        super("O ID informado na URL não corresponde ao ID do corpo da requisição.");
    }
}
