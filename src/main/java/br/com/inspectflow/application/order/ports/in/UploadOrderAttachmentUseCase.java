package br.com.inspectflow.application.order.ports.in;

import br.com.inspectflow.application.order.dto.OrderAttachmentRequest;
import br.com.inspectflow.application.order.dto.OrderDetailResponse;

import java.util.UUID;

public interface UploadOrderAttachmentUseCase {
    OrderDetailResponse execute(UUID order, UUID userId , OrderAttachmentRequest dto);
}
