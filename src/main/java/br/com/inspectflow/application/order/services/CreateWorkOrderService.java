package br.com.inspectflow.application.order.services;

import br.com.inspectflow.application.http.handlers.EquipmentComponentNotFoundExceprion;
import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.order.dto.CreateOrderRequest;
import br.com.inspectflow.application.order.dto.OrderResponse;
import br.com.inspectflow.application.order.mappers.WorkOrderMapper;
import br.com.inspectflow.application.order.ports.in.CreateWorkOrderUseCase;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateWorkOrderService implements CreateWorkOrderUseCase {
    private final WorkOrderRepository repository;
    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;
    private final StockItemRepository stockItemRepository;


    @Override
    @Transactional
    public OrderResponse execute(CreateOrderRequest dto, Authentication authUser) {

        User user = userRepository.findByEmail(authUser.getName()).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));

        Equipment equipment = equipmentRepository.findById(dto.equipmentId()).orElseThrow(EquipmentComponentNotFoundExceprion::new);

        WorkOrder order = WorkOrderMapper.fromRequest(dto);

        order.setEquipment(equipment);

        order.setAssignee(user);

        setInfoMessage(order);

        repository.save(order);

        return OrderResponse.from(order);
    }


    private void setInfoMessage(WorkOrder order) {
        if (order.getEquipmentName().isEmpty()) return;

        for (var part : order.getParts()) {
            if (part.isFromStock()) {
                var stockItem = stockItemRepository.getReferenceById(part.stockId());
                if (stockItem.getQuantity() < part.quantity()) {
                    order.addSystemInfo("A quantidade disponível em estoque do item "
                            + part.name().toUpperCase() + " (" + stockItem.getQuantity() + " em estoque)"
                            + " é menor que a quantidade (" + part.quantity() + ") necessária(s) para a manutenção." );
                }
                ;
            } else {
                order.addSystemInfo("Um ou mais itens necessários para a manutenção não estão cadastrados no estoque!");
            }


        }
    }

}
