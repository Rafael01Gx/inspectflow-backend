package br.com.inspectflow.application.order.ports.in;

import java.util.UUID;

public interface DeleteOrderAttachmentUseCase {
    Void execute(UUID id, UUID attachmentId);
}
