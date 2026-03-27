package br.com.inspectflow.application.http.handlers;

public class WorkerOrderNotFoundException extends RuntimeException{
    public WorkerOrderNotFoundException() {
        super("Ordem de serviço não encontrada.");
    }

    public WorkerOrderNotFoundException(String message) {
        super(message);
    }

}
