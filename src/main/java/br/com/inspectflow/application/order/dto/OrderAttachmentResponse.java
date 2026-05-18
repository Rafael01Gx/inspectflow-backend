package br.com.inspectflow.application.order.dto;

import br.com.inspectflow.domain.order.enums.OrderAttachmentType;
import br.com.inspectflow.domain.order.models.OrderAttachment;

import java.util.UUID;

public record OrderAttachmentResponse(
        UUID id,
        String fileName,
        String fileUrl,
        OrderAttachmentType type
) {

    public static OrderAttachmentResponse from(OrderAttachment attachment){
        return new OrderAttachmentResponse(
                attachment.getId(),
                attachment.getFileName(),
                attachment.getFileUrl(),
                attachment.getType()
        );
    }
}
