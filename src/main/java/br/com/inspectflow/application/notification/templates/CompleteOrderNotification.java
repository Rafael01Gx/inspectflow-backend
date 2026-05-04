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
public class CompleteOrderNotification {
    private final ObjectMapper mapper = new ObjectMapper();
    private final NotificationService notificationService;

    public void execute(WorkOrder order) {
        Set<Role> grupo = Set.of(Role.ADMINISTRADOR,Role.SUPERVISOR,Role.GESTOR,Role.LIDER);
        String message = String.format(
                "A Ordem de Serviço \"%s\" (%s) foi concluída com sucesso.",
                order.getTitle(),
                order.getEquipmentName()
        );

        Map<String, Object> metadataMap = new HashMap<>();
        metadataMap.put("equipamento", order.getEquipmentName());
        metadataMap.put("titulo", order.getTitle());
        metadataMap.put("resumo", order.getPerformedWork());
        metadataMap.put("status_atual", order.getOrderStatus().getValue());
        metadataMap.put("responsavel", order.getAssignee().getName());
        metadataMap.put("concluida_em", order.getCompletionDate() != null ? FormatDateUtils.format(order.getCompletionDate()) : null);
        metadataMap.put("url", "maintenance/" + order.getId());

        String metadata;

        try {
            metadata = mapper.writeValueAsString(metadataMap);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao serializar metadata", e);
        }



        notificationService.sendToGroup(grupo, SendNotificationDto.builder()
                .title("Ordem de serviço concluída")
                .type(NotificationType.SUCCESS)
                .message(message)
                .metadata(metadata)
                .build());
    }
}

