package br.com.inspectflow.application.dashboard.services;

import br.com.inspectflow.application.dashboard.dto.OverdueInspectionDto;
import br.com.inspectflow.application.dashboard.ports.in.PlantOverdueInspectionUseCase;
import br.com.inspectflow.application.dashboard.ports.out.PlantHealthQueryRepository;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlantOverdueInspectionService implements PlantOverdueInspectionUseCase {
    private final PlantHealthQueryRepository repository;

    @Override
    @Cacheable(value = "plantHealthOverdue", key = "#pageable.pageNumber.toString() + ':s:' + #pageable.pageSize.toString()")
    @Transactional(readOnly = true)
    @Observed(name = "dashboard.plant-health.overdue", contextualName = "overdue inspections")
    public PagedResponse<OverdueInspectionDto> execute(Pageable pageable) {
        Page<OverdueInspectionDto> page = repository.findOverdueInspections(pageable);
        return new PagedResponse<OverdueInspectionDto>(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize(),
                page.isLast()
        );
    }
}
