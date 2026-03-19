package br.com.inspectflow.application.http.handlers;

public class BucketFileNotFoundExeption extends RuntimeException {
    public BucketFileNotFoundExeption(String message) {
        super(message);
    }

    public BucketFileNotFoundExeption() {
        super("Arquivo não encontrado");
    }
}
