package br.com.inspectflow.application.http.handlers;

public class TokenExpiredException extends  RuntimeException {

    public TokenExpiredException() {
        super("Este link expirou. Solicite um novo.");
    }
}


