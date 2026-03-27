package br.com.inspectflow.application.order.mappers;

import br.com.inspectflow.application.order.dto.CreateOrderRequest;
import br.com.inspectflow.domain.order.models.WorkOrder;

public class WorkOrderMapper {

    public static WorkOrder fromRequest(CreateOrderRequest dto){
        WorkOrder order = WorkOrder.builder()
                .title(dto.title())
                .description(dto.description())
                .equipmentName(dto.equipmentName())
                .orderPriority(dto.orderPriority())
                .dueDate(dto.dueDate())
                .build();
        if (!dto.parts().isEmpty()){
            dto.parts().forEach(order::addPart);
        }

        return order;
    }
}
