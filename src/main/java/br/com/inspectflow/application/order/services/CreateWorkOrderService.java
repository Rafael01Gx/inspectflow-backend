package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.http.handlers.EquipmentComponentNotFoundExceprion;
import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.notification.templates.CreateOrderNotification;
import br.com.inspectflow.application.order.dto.CreateOrderRequest;
import br.com.inspectflow.application.order.dto.OrderResponse;
import br.com.inspectflow.application.order.helpers.SetInfoStockMessage;
import br.com.inspectflow.application.order.mappers.WorkOrderMapper;
import br.com.inspectflow.application.order.ports.in.CreateWorkOrderUseCase;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import br.com.inspectflow.domain.notification.enums.NotificationType;
import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateWorkOrderService implements CreateWorkOrderUseCase {
    private final WorkOrderRepository repository;
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;
    private final SetInfoStockMessage setInfoStockMessage;
    private final CreateOrderNotification notification;


    @Override
    @Transactional
    public OrderResponse execute(CreateOrderRequest dto, Authentication authUser) {

        User user = userRepository.findByEmail(authUser.getName()).orElseThrow(UserNotFoundException::new);

        Equipment equipment = equipmentRepository.findById(dto.equipmentId()).orElseThrow(EquipmentComponentNotFoundExceprion::new);

        WorkOrder order = WorkOrderMapper.fromRequest(dto);

        order.setEquipment(equipment);

        order.setAssignee(user);

        setInfoStockMessage.execute(order);

        repository.save(order);

        notification.execute(order, NotificationType.INFO);

        return OrderResponse.from(order);
    }




}
