package br.com.inspectflow.application.dashboard.ports.in;

import br.com.inspectflow.application.dashboard.dto.StockLowQuantityDto;

import java.util.List;

public interface StockLowQuantityUseCase {
    List<StockLowQuantityDto> execute();
}
