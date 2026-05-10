package br.com.inspectflow.application.notification.templates;

import br.com.inspectflow.application.notification.dto.SendNotificationDto;
import br.com.inspectflow.application.notification.services.NotificationService;
import br.com.inspectflow.application.order.events.WorkOrderCreatedEvent;
import br.com.inspectflow.application.utils.FormatDateUtils;
import br.com.inspectflow.domain.notification.enums.NotificationType;
import br.com.inspectflow.domain.user.enums.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CreateOrderNotification {
    private final ObjectMapper mapper = new ObjectMapper();
    private final NotificationService notificationService;

    public void execute(WorkOrderCreatedEvent order, NotificationType notificationType) {
        Set<Role> grupo = Set.of(Role.ADMINISTRADOR,Role.SUPERVISOR,Role.GESTOR,Role.LIDER);
        String message = String.format(
                "Você foi designado como responsável pela OS \"%s\" (%s).",
                order.title(),
                order.equipmentName()
        );

        Map<String, Object> metadataMap = new HashMap<>();
        metadataMap.put("equipamento", order.equipmentName());
        metadataMap.put("resumo", order.title());
        metadataMap.put("prioridade", order.orderPriority().getValue());
        metadataMap.put("status_atual", order.orderStatus().getValue());
        metadataMap.put("data_agendada", order.dueDate() != null ? FormatDateUtils.format(order.dueDate()) : null);
        metadataMap.put("novo_responsavel", order.assignee());
        metadataMap.put("data_criacao", order.createdAt() != null ? FormatDateUtils.format(order.createdAt()) : FormatDateUtils.format(LocalDateTime.now()));
        metadataMap.put("url", "maintenance/" + order.id());

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
