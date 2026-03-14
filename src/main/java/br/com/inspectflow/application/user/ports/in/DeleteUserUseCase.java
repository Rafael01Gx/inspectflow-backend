package br.com.inspectflow.application.user.ports.in;

import java.util.UUID;

public interface DeleteUserUseCase {
    void execute(UUID id);
}
