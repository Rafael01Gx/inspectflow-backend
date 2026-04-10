package br.com.inspectflow.application.stock.ports.in;

import br.com.inspectflow.application.stock.dto.StockItemResponse;

import java.util.List;
import java.util.UUID;

public interface FindAllStockItemByEquipmentIdUseCase {

    List<StockItemResponse> execute(UUID equipmentId);
}
