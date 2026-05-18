package br.com.inspectflow.application.http.handlers;

public class AttachmentException extends  RuntimeException{
    public AttachmentException(String message) {
        super(message);
    }

    public AttachmentException(String message, Throwable cause) {
        super(message, cause);
    }
    public AttachmentException() {
        super("Erro ao processar o arquivo.");
    }
}
