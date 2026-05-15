package br.com.inspectflow.application.order.events;

import br.com.inspectflow.domain.order.enums.OrderAttachmentType;
import br.com.inspectflow.domain.order.models.OrderAttachment;
import br.com.inspectflow.domain.order.models.WorkOrder;

public record WorkOrderAddDocumentEvent(
        String numeroOrdemServico,
        String statusOrdemServico,
        String equipmentName,
        OrderAttachmentType tipoDocumento,
        String nomeArquivo,
        String assigneeEmail
) {

    public static  WorkOrderAddDocumentEvent from(WorkOrder workOrder, OrderAttachment orderAttachment) {
        return new WorkOrderAddDocumentEvent(
                workOrder.getId().toString(),
                workOrder.getOrderStatus().toString(),
                workOrder.getEquipmentName(),
                orderAttachment.getType(),
                orderAttachment.getFileName(),
                workOrder.getAssignee().getEmail()
        );
    }
}
