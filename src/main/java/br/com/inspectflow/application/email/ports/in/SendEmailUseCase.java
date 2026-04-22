package br.com.inspectflow.application.email.ports.in;

public interface SendEmailUseCase {
    void execute(String to, String subject, String body);
}
