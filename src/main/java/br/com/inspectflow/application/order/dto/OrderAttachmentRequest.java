package br.com.inspectflow.application.order.dto;

import br.com.inspectflow.domain.order.enums.OrderAttachmentType;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record OrderAttachmentRequest(

        @NotNull(message = "Id da ordem de serviço é obrigatório")
        UUID orderId,

        @NotNull(message = "Tipo do anexo é obrigatório")
        OrderAttachmentType type,

        @NotNull(message = "Arquivo é obrigatório")
        MultipartFile file
) {
}
