package br.com.inspectflow.application.email.ports.in;

import br.com.inspectflow.application.email.dto.SendWorkOrderUpdateMailSend;

public interface SendWorkOrderUpdateMailUseCase {
    void execute(
            SendWorkOrderUpdateMailSend request
    );
}
