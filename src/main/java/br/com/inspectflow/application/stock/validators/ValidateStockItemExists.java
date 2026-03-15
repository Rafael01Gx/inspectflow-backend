package br.com.inspectflow.application.stock.validators;

import br.com.inspectflow.application.http.handlers.StockItemNotFoundException;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidateStockItemExists implements StockItemValidator<Long>{

    private final StockItemRepository repository;

    @Override
    public void execute(Long id) {
        if(!(repository.existsById(id))){
            throw new StockItemNotFoundException("Item não encontrado");
        };
    }
}
