package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.http.handlers.EquipmentNotFoundException;
import br.com.inspectflow.application.stock.dto.StockItemResponse;
import br.com.inspectflow.application.stock.ports.in.FindAllByEquipmentIdUseCase;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindAllByEquipmentIdService implements FindAllByEquipmentIdUseCase {
    private final StockItemRepository repository;
    private final EquipmentRepository equipmentRepository;


    @Override
    public List<StockItemResponse> execute(UUID equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId).orElseThrow(EquipmentNotFoundException::new);
        var stockItems = equipment.getPartsInStock();

        if(stockItems.isEmpty()) return List.of();

        return stockItems.stream().map(StockItemResponse::from).toList();
    }
}
