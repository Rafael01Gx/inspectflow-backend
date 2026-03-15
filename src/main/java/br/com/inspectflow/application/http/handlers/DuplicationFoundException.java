package br.com.inspectflow.application.http.handlers;

public class DuplicationFoundException extends RuntimeException{
    public DuplicationFoundException(String message){
        super(message);
    }
}
