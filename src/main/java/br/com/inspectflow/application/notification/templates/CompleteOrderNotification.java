package br.com.inspectflow.application.notification.templates;

import br.com.inspectflow.application.notification.dto.SendNotificationDto;
import br.com.inspectflow.application.notification.services.NotificationService;
import br.com.inspectflow.application.order.dto.CompleteWorkOrderNotificationDto;
import br.com.inspectflow.application.utils.FormatDateUtils;
import br.com.inspectflow.domain.notification.enums.NotificationType;
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

    public void execute(CompleteWorkOrderNotificationDto order) {
        Set<Role> grupo = Set.of(Role.ADMINISTRADOR,Role.SUPERVISOR,Role.GESTOR,Role.LIDER);
        String message = String.format(
                "A Ordem de Serviço \"%s\" (%s) foi concluída com sucesso.",
                order.title(),
                order.equipmentName()
        );

        Map<String, Object> metadataMap = new HashMap<>();
        metadataMap.put("equipamento", order.equipmentName());
        metadataMap.put("titulo", order.title());
        metadataMap.put("resumo", order.performedWork());
        metadataMap.put("status_atual", order.orderStatus());
        metadataMap.put("responsavel", order.assignee());
        metadataMap.put("concluida_em", order.completionDate() != null ? FormatDateUtils.format(order.completionDate()) : null);
        metadataMap.put("url", "maintenance/" + order.id());

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

