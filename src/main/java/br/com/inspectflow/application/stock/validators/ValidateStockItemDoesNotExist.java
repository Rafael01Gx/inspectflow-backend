package br.com.inspectflow.application.stock.validators;

import br.com.inspectflow.application.http.handlers.DuplicationFoundException;
import br.com.inspectflow.application.stock.dto.CreateStockItemRequest;
import br.com.inspectflow.domain.stock.repositories.StockItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ValidateStockItemDoesNotExist implements StockItemValidator<CreateStockItemRequest> {

    private final StockItemRepository repository;

    @Override
    public void execute(CreateStockItemRequest dto) {
        if(repository.existsByNameOrSupplierCode(dto.name(), dto.supplierCode())){
            throw new DuplicationFoundException("Já existe um item com esse nome ou código de fornecedor");
        }
    }
}
