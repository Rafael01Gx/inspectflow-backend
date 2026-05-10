package br.com.inspectflow.application.dashboard.services;

import br.com.inspectflow.application.dashboard.dto.StockLowQuantityDto;
import br.com.inspectflow.application.dashboard.ports.in.StockLowQuantityUseCase;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockLowQuantityService implements StockLowQuantityUseCase {
    private final StockItemRepository stockItemRepository;

    @Override
    @Cacheable(value = "dashboardStockItems", key = "'lowQuantity'")
    @Observed(name = "dashboard.low-quantity",
    contextualName = "itens de estoque com quantidade baixa")
    @Transactional(readOnly = true)
    public List<StockLowQuantityDto> execute() {
        return stockItemRepository.findByLowQuantity().stream()
                .map(item -> new StockLowQuantityDto(item.getId(), item.getName(), item.getQuantity(), item.getMinQuantity()))
                .collect(Collectors.toList());
    }
}
