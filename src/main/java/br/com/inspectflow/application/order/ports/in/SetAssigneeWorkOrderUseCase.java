package br.com.inspectflow.application.order.ports.in;

import java.util.UUID;

public interface SetAssigneeWorkOrderUseCase {

    void execute(UUID id, UUID assigneeId);
}
