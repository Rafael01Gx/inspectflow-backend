package br.com.inspectflow.application.dashboard.services;

import br.com.inspectflow.application.dashboard.dto.CriticalStockDto;
import br.com.inspectflow.application.dashboard.ports.in.CriticalStockUseCase;
import br.com.inspectflow.application.dashboard.ports.out.PlantHealthQueryRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CriticalStockService implements CriticalStockUseCase {
    private final PlantHealthQueryRepository repository;

    @Override
    @Cacheable(value = "plantHealthCriticalStock", key = "'critical-stock'")
    @Transactional(readOnly = true)
    @Observed(name = "dashboard.critical-stock",
    contextualName = "itens de estoque em nível crítico")
    public List<CriticalStockDto> execute() {
        return new ArrayList<CriticalStockDto>(repository.findCriticalStockItems());
    }
}
