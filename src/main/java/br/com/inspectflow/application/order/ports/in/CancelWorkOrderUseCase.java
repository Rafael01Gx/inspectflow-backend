package br.com.inspectflow.application.order.ports.in;

import br.com.inspectflow.application.order.dto.CancelOrderRequest;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface CancelWorkOrderUseCase {

    void execute(UUID id, CancelOrderRequest dto, Authentication authUser);
}
