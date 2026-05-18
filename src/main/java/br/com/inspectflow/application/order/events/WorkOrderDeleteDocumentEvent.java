package br.com.inspectflow.application.order.events;

public record WorkOrderDeleteDocumentEvent(
        String fileUrl
) {
}
