package br.com.inspectflow.application.email.ports.in;

public interface FirstAccessMailUseCase {
    void execute(String to, String nome, String tempPassword);
}
