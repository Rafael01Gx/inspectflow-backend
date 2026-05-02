package br.com.inspectflow.application.http.handlers;

public class NotificationNotFoundException extends RuntimeException{

    public NotificationNotFoundException(String message) {
        super(message);
    }

    public NotificationNotFoundException() {
        super("Notificação não encontrada");
    }
}
