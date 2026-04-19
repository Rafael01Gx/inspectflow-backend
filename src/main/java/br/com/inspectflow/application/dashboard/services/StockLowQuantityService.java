package br.com.inspectflow.application.dashboard.services;

import br.com.inspectflow.application.dashboard.dto.StockLowQuantityDto;
import br.com.inspectflow.application.dashboard.ports.in.StockLowQuantityUseCase;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockLowQuantityService implements StockLowQuantityUseCase {
    private final StockItemRepository stockItemRepository;

    @Override
    @Cacheable(value = "dashboardStockItems", key = "'lowQuantity'")
    public List<StockLowQuantityDto> execute() {
        return stockItemRepository.findByLowQuantity().stream()
                .map(item -> new StockLowQuantityDto(item.getId(), item.getName(), item.getQuantity(), item.getMinQuantity()))
                .collect(Collectors.toList());
    }
}
