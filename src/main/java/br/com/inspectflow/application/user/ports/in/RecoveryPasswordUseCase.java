package br.com.inspectflow.application.user.ports.in;

public interface RecoveryPasswordUseCase {
    void execute(String email);
}
