package br.com.inspectflow.application.order.events;

import br.com.inspectflow.domain.order.enums.OrderAttachmentType;
import br.com.inspectflow.domain.order.models.OrderAttachment;
import br.com.inspectflow.domain.order.models.WorkOrder;

import java.util.UUID;

public record WorkOrderAddDocumentEvent(
        String numeroOrdemServico,
        String statusOrdemServico,
        String equipmentName,
        String equipmentCode,
        OrderAttachmentType tipoDocumento,
        String nomeArquivo,
        String assigneeEmail,

        UUID assigneeId
) {

    public static  WorkOrderAddDocumentEvent from(WorkOrder workOrder, OrderAttachment orderAttachment) {
        return new WorkOrderAddDocumentEvent(
                workOrder.getId().toString(),
                workOrder.getOrderStatus().getValue(),
                workOrder.getEquipmentName(),
                workOrder.getEquipment().getCode(),
                orderAttachment.getType(),
                orderAttachment.getFileName(),
                workOrder.getAssignee().getEmail(),
                workOrder.getAssignee().getId()
        );
    }
}
