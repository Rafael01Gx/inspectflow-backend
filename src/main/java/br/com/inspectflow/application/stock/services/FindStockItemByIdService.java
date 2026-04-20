package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.bucket.ports.in.CreatePresignedUrlUseCase;
import br.com.inspectflow.application.http.handlers.StockItemNotFoundException;
import br.com.inspectflow.application.stock.dto.StockItemResponse;
import br.com.inspectflow.application.stock.ports.in.FindStockItemByIdUseCase;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindStockItemByIdService implements FindStockItemByIdUseCase {
    private final StockItemRepository repository;
    private final CreatePresignedUrlUseCase presignedUrl;

    @Override
    public StockItemResponse execute(Long id) {
        var stockItem = repository.findById(id).orElseThrow(StockItemNotFoundException::new);

        if (stockItem.getImageUrl() != null && !stockItem.getImageUrl().isEmpty()) {
            var url = presignedUrl.execute(stockItem.getImageUrl());
            stockItem.setImageUrl(url);
        }

        return StockItemResponse
                .from(stockItem);
    }
}
