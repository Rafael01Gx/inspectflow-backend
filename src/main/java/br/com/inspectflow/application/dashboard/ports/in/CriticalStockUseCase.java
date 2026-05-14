package br.com.inspectflow.application.dashboard.ports.in;

import br.com.inspectflow.application.dashboard.dto.CriticalStockDto;

import java.util.List;

public interface CriticalStockUseCase {

    List<CriticalStockDto> execute();
}
