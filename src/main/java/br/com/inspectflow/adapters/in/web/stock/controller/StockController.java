package br.com.inspectflow.adapters.in.web.stock.controller;

import br.com.inspectflow.application.stock.dto.*;
import br.com.inspectflow.application.stock.ports.in.*;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final CreateStockItemsUseCase createStockItems;
    private final FindAllStockItemsUseCase findAllStockItems;
    private final FindStockItemByIdUseCase findStockItemById;
    private final FindAllStockItemByEquipmentIdUseCase findAllByEquipmentId;
    private final UpdateStockItemUseCase updateStockItem;
    private final DeductStockItemUseCase deductStockItem;
    private final SearchByNameStockItemUseCase searchByNameStockItem;
    private final FindAllStockItemUsageUseCase findAllStockItemUsage;


    @GetMapping
    public ResponseEntity<PagedResponse<StockItemResponse>> getAll(@PageableDefault Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),null, "DESC");
        return ResponseEntity.ok(findAllStockItems.execute(pageRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<StockItemResponse>> getAll(){
        return ResponseEntity.ok(findAllStockItems.execute());
    }

    @GetMapping("/search")
    public ResponseEntity<List<StockItemResponse>> search(@RequestParam String q ){
        return ResponseEntity.ok(searchByNameStockItem.execute(q));
    }

    @GetMapping("/search/equipment/{equipmentId}")
    public ResponseEntity<List<StockItemResponse>> search(@PathVariable UUID equipmentId ){
        return ResponseEntity.ok(findAllByEquipmentId.execute(equipmentId));
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<PagedResponse<StockItemUsageResponse>> getHistory(@PathVariable Long id, @PageableDefault Pageable pageable){
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),null, "DESC");
        return ResponseEntity.ok(findAllStockItemUsage.execute(id,pageRequest));
    }




    @GetMapping("/{id}")
    public ResponseEntity<StockItemResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(findStockItemById.execute(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StockItemResponse> createItem(@RequestPart("item") @Valid CreateStockItemRequest dto,@RequestPart(value = "file", required = false) MultipartFile file){
        return ResponseEntity.ok(createStockItems.execute(dto,file));
    }

    @PutMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StockItemResponse> updateItem(@PathVariable Long id, @RequestPart("item") @Valid UpdateStockItemRequest dto,@RequestPart(value = "file", required = false) MultipartFile file){
        return ResponseEntity.ok(updateStockItem.execute(id,dto,file));
    }

    @PostMapping("/deduct/{id}")
    public ResponseEntity<String> deductStock(@PathVariable Long id, @RequestBody @Valid DeductStockRequest dto){
        deductStockItem.execute(id,dto);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(){
        return ResponseEntity.ok("Um item não pode deletado!");
    }
}
