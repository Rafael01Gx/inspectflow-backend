package br.com.inspectflow.application.dashboard.ports.in;

import br.com.inspectflow.application.dashboard.dto.OverdueInspectionDto;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import org.springframework.data.domain.Pageable;

public interface PlantOverdueInspectionUseCase {
    PagedResponse<OverdueInspectionDto> execute(Pageable pageable);
}
