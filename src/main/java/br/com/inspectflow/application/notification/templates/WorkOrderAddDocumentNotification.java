package br.com.inspectflow.application.notification.templates;

import br.com.inspectflow.application.notification.dto.SendNotificationDto;
import br.com.inspectflow.application.notification.services.NotificationService;
import br.com.inspectflow.application.order.events.WorkOrderAddDocumentEvent;
import br.com.inspectflow.domain.notification.enums.NotificationType;
import br.com.inspectflow.domain.order.enums.OrderAttachmentType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WorkOrderAddDocumentNotification {
    private final ObjectMapper mapper = new ObjectMapper();
    private final NotificationService notificationService;

    public void execute(WorkOrderAddDocumentEvent event, NotificationType notificationType) {
        String message = String.format(
                "Um novo documento (%s) foi adicionado à OS \"%s\"."
        );

        Map<String, Object> metadataMap = new HashMap<>();
        metadataMap.put("equipamento", event.equipmentName()+ "[" + event.equipmentCode() + "]");
        metadataMap.put("resumo", "Documento disponível para visualização");
        metadataMap.put("tipo_documento",  switch (event.tipoDocumento()){
            case OrderAttachmentType.APR->
                    "Analise preliminar de Risco".toUpperCase();
            case OrderAttachmentType.OS->
                    "Ordem de execução de Serviço".toUpperCase();
        });
        metadataMap.put("url", "maintenance/" + event.numeroOrdemServico());

        String metadata;

        try {
            metadata = mapper.writeValueAsString(metadataMap);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao serializar metadata", e);
        }

        notificationService.sendToUser(SendNotificationDto.builder()
                .recipientId(event.assigneeId())
                .title("Novo documento na Ordem de Serviço")
                .type(notificationType)
                .message(message)
                .metadata(metadata)
                .build());
    }
}
