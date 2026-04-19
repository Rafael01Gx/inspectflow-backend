package br.com.inspectflow.application.dashboard.ports.in;

import br.com.inspectflow.application.dashboard.dto.KpiSummaryDto;

public interface KpiSummaryUseCase {
    KpiSummaryDto execute();
}
