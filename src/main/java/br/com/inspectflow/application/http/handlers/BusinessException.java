package br.com.inspectflow.application.http.handlers;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
