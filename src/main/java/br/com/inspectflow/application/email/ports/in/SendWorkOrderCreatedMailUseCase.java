package br.com.inspectflow.application.email.ports.in;

import br.com.inspectflow.application.email.dto.SendWorkOrderCreatedMailRequest;

public interface SendWorkOrderCreatedMailUseCase {
    void execute(
            SendWorkOrderCreatedMailRequest request
    );
}
