package br.com.inspectflow.application.dashboard.services;

import br.com.inspectflow.application.dashboard.dto.OpenOrderByPriorityDto;
import br.com.inspectflow.application.dashboard.ports.in.OpenOrderByPriorityUseCase;
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
public class OpenOrderByPriorityService implements OpenOrderByPriorityUseCase {

    private final PlantHealthQueryRepository repository;

    @Override
    @Cacheable(value = "plantHealthOpenOrders", key = "'open-orders'")
    @Transactional(readOnly = true)
    @Observed(name = "dashboard.open-orders", contextualName = "ordens de serviço em aberto por prioridade")
    public List<OpenOrderByPriorityDto> execute() {

        return new ArrayList<OpenOrderByPriorityDto>(repository.findOpenOrdersByPriority());
    }
}
