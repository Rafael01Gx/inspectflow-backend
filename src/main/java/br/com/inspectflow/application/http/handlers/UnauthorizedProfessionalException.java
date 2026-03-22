package br.com.inspectflow.application.http.handlers;

public class UnauthorizedProfessionalException extends RuntimeException{

    public UnauthorizedProfessionalException(String message){
        super(message);
    }

    public UnauthorizedProfessionalException(){
        super("O profissional informado não possui as credenciais ou habilitações necessárias para realizar esta operação.");
    }
}
