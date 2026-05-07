package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.http.handlers.EquipmentNotFoundException;
import br.com.inspectflow.application.stock.dto.StockItemResponse;
import br.com.inspectflow.application.stock.ports.in.FindAllStockItemByEquipmentIdUseCase;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindAllStockItemByEquipmentIdService implements FindAllStockItemByEquipmentIdUseCase {
    private final EquipmentRepository equipmentRepository;


    @Override
    @Transactional(readOnly = true)
    public List<StockItemResponse> execute(UUID equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow(EquipmentNotFoundException::new);
        var stockItems = equipment.getPartsInStock();

        if(stockItems.isEmpty()) return List.of();

        return stockItems.stream().map(StockItemResponse::from).toList();
    }
}
