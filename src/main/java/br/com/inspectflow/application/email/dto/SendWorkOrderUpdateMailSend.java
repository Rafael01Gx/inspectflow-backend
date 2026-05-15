package br.com.inspectflow.application.email.dto;

import br.com.inspectflow.domain.order.enums.OrderAttachmentType;

public record SendWorkOrderUpdateMailSend(
        String numeroOrdemServico,
        String statusOrdemServico,
        String equipmentName,
        OrderAttachmentType tipoDocumento,
        String nomeArquivo,
        String assigneeEmail
) {
}
