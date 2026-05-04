package br.com.inspectflow.application.notification.templates;

import br.com.inspectflow.application.notification.dto.SendNotificationDto;
import br.com.inspectflow.application.notification.services.NotificationService;
import br.com.inspectflow.application.utils.FormatDateUtils;
import br.com.inspectflow.domain.notification.enums.NotificationType;
import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.user.enums.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CreateOrderNotification {
    private final ObjectMapper mapper = new ObjectMapper();
    private final NotificationService notificationService;

    public void execute(WorkOrder order,NotificationType notificationType) {
        Set<Role> grupo = Set.of(Role.ADMINISTRADOR,Role.SUPERVISOR,Role.GESTOR,Role.LIDER);
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



        notificationService.sendToGroup(grupo,SendNotificationDto.builder()
                .title("Nova responsabilidade atribuída")
                .type(notificationType)
                .message(message)
                .metadata(metadata)
                .build());
    }
}
