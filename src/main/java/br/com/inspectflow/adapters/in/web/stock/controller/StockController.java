package br.com.inspectflow.adapters.in.web.stock.controller;

import br.com.inspectflow.application.stock.dto.CreateStockItemRequest;
import br.com.inspectflow.application.stock.dto.DeductStockRequest;
import br.com.inspectflow.application.stock.dto.StockItemResponse;
import br.com.inspectflow.application.stock.dto.UpdateStockItemRequest;
import br.com.inspectflow.application.stock.ports.in.*;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final CreateStockItemsUseCase createStockItems;
    private final FindAllStockItemsUseCase findAllStockItems;
    private final FindStockItemByIdUseCase findStockItemById;
    private final UpdateStockItemUseCase updateStockItem;
    private final DeductStockItemUseCase deductStockItem;

    @GetMapping
    public ResponseEntity<PagedResponse<StockItemResponse>> getAll(@PageableDefault Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(findAllStockItems.execute(pageRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockItemResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(findStockItemById.execute(id));
    }

    @PostMapping
    public ResponseEntity<StockItemResponse> createItem(@RequestBody @Valid CreateStockItemRequest dto){
        return ResponseEntity.ok(createStockItems.execute(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockItemResponse> updateItem(@PathVariable Long id, @RequestBody @Valid UpdateStockItemRequest dto){
        return ResponseEntity.ok(updateStockItem.execute(id,dto));
    }

    @PostMapping("/deduct/{id}")
    public ResponseEntity<String> deductStock(@PathVariable Long id, @RequestBody @Valid DeductStockRequest dto){
        deductStockItem.execute(id,dto);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(){
        return ResponseEntity.ok("Este item não pode deletado!");
    }
}
