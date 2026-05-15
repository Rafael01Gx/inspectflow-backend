package br.com.inspectflow.adapters.in.web.stock.controller;

import br.com.inspectflow.adapters.in.mappers.PageableRequestMapper;
import br.com.inspectflow.application.stock.dto.*;
import br.com.inspectflow.application.stock.ports.in.*;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<PagedResponse<StockItemResponse>> getAll(@PageableDefault(size = 20, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.ok(findAllStockItems.execute(PageableRequestMapper.fromRequest(pageable)));
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
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

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/{id}/history")
    public ResponseEntity<PagedResponse<StockItemUsageResponse>> getHistory(@PathVariable Long id, @PageableDefault Pageable pageable){
        return ResponseEntity.ok(findAllStockItemUsage.execute(id,PageableRequestMapper.fromRequest(pageable)));
    }


    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/{id}")
    public ResponseEntity<StockItemResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(findStockItemById.execute(id));
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StockItemResponse> createItem(@RequestPart("item") @Valid CreateStockItemRequest dto,@RequestPart(value = "file", required = false) MultipartFile file){
        return ResponseEntity.ok(createStockItems.execute(dto,file));
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @PutMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StockItemResponse> updateItem(@PathVariable Long id, @RequestPart("item") @Valid UpdateStockItemRequest dto,@RequestPart(value = "file", required = false) MultipartFile file){
        return ResponseEntity.ok(updateStockItem.execute(id,dto,file));
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @PostMapping("/deduct/{id}")
    public ResponseEntity<String> deductStock(@PathVariable Long id, @RequestBody @Valid DeductStockRequest dto){
        deductStockItem.execute(id,dto);
        return ResponseEntity.ok("Success");
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(){
        return ResponseEntity.ok("Um item não pode deletado!");
    }
}
