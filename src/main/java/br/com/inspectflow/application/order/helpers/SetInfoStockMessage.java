package br.com.inspectflow.application.order.helpers;

import br.com.inspectflow.domain.order.models.MaintenancePart;
import br.com.inspectflow.domain.order.models.WorkOrder;
import br.com.inspectflow.domain.stock.models.StockItem;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SetInfoStockMessage {

    private final StockItemRepository stockItemRepository;

    public void execute(WorkOrder order) {

        StringBuilder stockRequisition = new StringBuilder();

        if (order.getEquipmentName() == null || order.getEquipmentName().isBlank()) {
            return;
        }

        order.getSystemInfo().removeIf(
                s -> s.contains("A quantidade disponível em estoque do item")
        );

        var stockIds = order.getParts().stream()
                .filter(MaintenancePart::isFromStock)
                .map(MaintenancePart::stockId)
                .toList();

        var stockMap = stockItemRepository.findAllById(stockIds)
                .stream()
                .collect(Collectors.toMap(StockItem::getId, Function.identity()));

        boolean hasNonStockItem = false;

        for (var part : order.getParts()) {

            if (part.isFromStock()) {

                var stockItem = stockMap.get(part.stockId());
                if (stockItem == null) continue;

                if (stockItem.getQuantity() < part.quantity()) {
                    order.addSystemInfo(
                            "A quantidade disponível em estoque do item "
                                    + part.name().toUpperCase()
                                    + " (" + stockItem.getQuantity() + " em estoque)"
                                    + " é menor que a quantidade ("
                                    + part.quantity()
                                    + ") necessária(s) para a manutenção."
                    );
                    stockRequisition.append("Descrição do item: ").append(stockItem.getName().toUpperCase())
                            .append(" - Qntd. necessária: ").append(part.quantity() - stockItem.getQuantity())
                            .append(stockItem.getSupplierCode().isEmpty() ? "" : " - Código: " + stockItem.getSupplierCode()).append("\n");
                }

            } else {
                hasNonStockItem = true;

                stockRequisition.append("Descrição do item: ").append(part.name().toUpperCase())
                        .append(" - Qntd. necessária: ").append(part.quantity()).append(" | Sem cadastro , solicitação externa!".toUpperCase())
                        .append("\n");
            }
        }

        if (hasNonStockItem) {
            order.addSystemInfo(
                    "Um ou mais itens necessários para a manutenção não estão cadastrados no estoque!"
            );
        }
        order.setStockRequisition(stockRequisition.toString());
        return;
    }
}
