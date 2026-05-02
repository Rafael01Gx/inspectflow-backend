package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.notification.services.NotificationService;
import br.com.inspectflow.application.order.ports.in.CreateSystemWorkOrderUseCase;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.order.enums.OrderPriority;
import br.com.inspectflow.domain.order.enums.OrderStatus;
import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import br.com.inspectflow.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateSystemWorkOrderService implements CreateSystemWorkOrderUseCase {

    private final WorkOrderRepository repository;
    private final NotificationService notificationService;


    @Override
    @Transactional
    public void execute(User user, Equipment equipment, List<String> descriptions) {

        String description = descriptions.stream()
                .map(d -> "- " + d)
                .collect(Collectors.joining("\n"));

        WorkOrder order = WorkOrder.builder()
                .title("Ordem de Serviço Automática - " + equipment.getCode())
                .description("""
                Esta ordem de serviço foi gerada automaticamente pelo sistema.
                Durante a inspeção realizada no(a) %s - (%s) ,
                foram encontradas falhas/problemas em componentes que afetam o funcionamento/operação segura do equipamento.
                %s
                """.formatted(equipment.getCode(),
                        equipment.getName(),
                        description
                        ))
                .equipmentName(equipment.getName())
                .orderPriority(OrderPriority.HIGH)
                .equipment(equipment)
                .orderStatus(OrderStatus.PENDING)
                .dueDate(LocalDate.now())
                .assignee(user)
                .build();
        order.addSystemInfo("Esta ordem de serviço foi gerada automaticamente pelo sistema.");
        repository.save(order);

    }
}
