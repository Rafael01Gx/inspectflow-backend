package br.com.inspectflow.application.notification.templates;

import br.com.inspectflow.application.notification.dto.SendNotificationDto;
import br.com.inspectflow.application.notification.services.NotificationService;
import br.com.inspectflow.application.utils.FormatDateUtils;
import br.com.inspectflow.domain.notification.enums.NotificationType;
import br.com.inspectflow.domain.order.models.WorkOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SetWorkOrderAssigneeNotification {

    private final ObjectMapper mapper = new ObjectMapper();
    private final NotificationService notificationService;


    public void execute(WorkOrder order) {


        String message = String.format(
                "Você foi designado como responsável pela OS \"%s\" (%s).",
                order.getTitle(),
                order.getEquipmentName()
        );

        Map<String, Object> metadataMap = new HashMap<>();
        metadataMap.put("equipamento", order.getEquipmentName());
        metadataMap.put("resumo", order.getTitle());
        metadataMap.put("prioridade", order.getOrderPriority().getValue());
        metadataMap.put("status_atual", order.getOrderStatus().getValue());
        metadataMap.put("data_agendada", order.getDueDate() != null ? FormatDateUtils.format(order.getDueDate()) : null);
        metadataMap.put("novo_responsavel", order.getAssignee().getName());
        metadataMap.put("data_criacao", order.getCreatedAt() != null ? FormatDateUtils.format(order.getCreatedAt()) : null);
        metadataMap.put("url", "maintenance/" + order.getId());

        String metadata;

        try {
            metadata = mapper.writeValueAsString(metadataMap);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao serializar metadata", e);
        }

        notificationService.sendToUser(
                SendNotificationDto.builder()
                        .recipientId(order.getAssignee().getId())
                        .title("Nova responsabilidade atribuída")
                        .type(NotificationType.INFO)
                        .message(message)
                        .metadata(metadata)
                        .build()
        );
    }
}
