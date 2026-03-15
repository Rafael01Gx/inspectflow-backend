package br.com.inspectflow.adapters.in.web.stock.controller;

import br.com.inspectflow.application.stock.dto.CreateStockItemRequest;
import br.com.inspectflow.application.stock.dto.StockItemResponse;
import br.com.inspectflow.application.stock.ports.in.CreateStockItemsUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final CreateStockItemsUseCase createStockItems;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<StockItemResponse> createItem(@RequestBody @Valid CreateStockItemRequest request){
        return ResponseEntity.ok(createStockItems.execute(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(){
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deductStock(){
        return ResponseEntity.ok().build();
    }
}
