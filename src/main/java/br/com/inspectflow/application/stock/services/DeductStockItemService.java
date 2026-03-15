package br.com.inspectflow.application.stock.services;

import br.com.inspectflow.application.common.validators.IdConsistencyValidator;
import br.com.inspectflow.application.http.handlers.StockItemNotFoundException;
import br.com.inspectflow.application.stock.dto.DeductStockRequest;
import br.com.inspectflow.application.stock.ports.in.DeductStockItemUseCase;
import br.com.inspectflow.domain.stock.models.StockItem;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeductStockItemService implements DeductStockItemUseCase {
    private final StockItemRepository repository;

    @Override
    @Transactional
    public void execute(Long id, DeductStockRequest dto) {
        IdConsistencyValidator.validate(id, dto.id());

        StockItem item = repository.findById(id).orElseThrow(()-> new StockItemNotFoundException("Item não encontrado"));

        if (item.getQuantity() < dto.quantity() ) throw new IllegalArgumentException("Quantidade insuficiente em estoque");

        item.deductStock(dto.quantity());

        repository.save(item);
    }
}
