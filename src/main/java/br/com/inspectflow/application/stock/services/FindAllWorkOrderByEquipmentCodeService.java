package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.order.dto.OrderResponse;
import br.com.inspectflow.application.stock.ports.in.FindAllWorkOrderByEquipmentCodeUseCase;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindAllWorkOrderByEquipmentCodeService implements FindAllWorkOrderByEquipmentCodeUseCase {
    private final WorkOrderRepository repository;

    @Override
    @Cacheable(value = "workOrders", key = "#equipmentCode")
    @Transactional(readOnly = true)
    public List<OrderResponse> execute(UUID equipmentCode) {
        return repository.findAllByEquipmentCode(equipmentCode).stream().map(OrderResponse::from).toList();
    }
}
