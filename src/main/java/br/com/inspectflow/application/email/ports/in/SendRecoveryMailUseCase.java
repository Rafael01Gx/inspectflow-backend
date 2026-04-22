package br.com.inspectflow.application.email.ports.in;

public interface SendRecoveryMailUseCase {
    void execute(String to, String name, String token);
}
