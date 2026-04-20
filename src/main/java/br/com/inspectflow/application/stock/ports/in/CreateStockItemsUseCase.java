package br.com.inspectflow.application.stock.ports.in;

import br.com.inspectflow.application.stock.dto.CreateStockItemRequest;
import br.com.inspectflow.application.stock.dto.StockItemResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CreateStockItemsUseCase {

    StockItemResponse execute(CreateStockItemRequest dto, MultipartFile file);
}
