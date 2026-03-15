package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.stock.dto.StockItemResponse;
import br.com.inspectflow.application.stock.ports.in.FindStockItemByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindStockItemByIdService implements FindStockItemByIdUseCase {
    @Override
    public StockItemResponse execute(Long id) {
        return null;
    }
}
