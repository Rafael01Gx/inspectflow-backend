package br.com.inspectflow.application.stock.ports.in;

import br.com.inspectflow.application.order.dto.OrderResponse;

import java.util.List;
import java.util.UUID;

public interface FindAllWorkOrderByEquipmentCodeUseCase {
    List<OrderResponse> execute(UUID equipmentCode);
}
