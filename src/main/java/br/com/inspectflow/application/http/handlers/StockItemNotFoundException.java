package br.com.inspectflow.application.http.handlers;

public class StockItemNotFoundException extends RuntimeException{
    public StockItemNotFoundException(String message){
        super(message);
    }
}
