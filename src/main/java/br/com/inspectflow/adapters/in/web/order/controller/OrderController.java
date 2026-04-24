package br.com.inspectflow.adapters.in.web.order.controller;

import br.com.inspectflow.application.order.dto.*;
import br.com.inspectflow.application.order.ports.in.*;
import br.com.inspectflow.application.stock.ports.in.FindAllWorkOrderByEquipmentCodeUseCase;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateWorkOrderUseCase createWorkOrder;
    private final FindAllWorkOrderUseCase findAllWorkOrder;
    private final FindWorkOrderByIdUseCase findWorkOrderById;
    private final FindAllWorkOrderByEquipmentCodeUseCase findAllWorkOrderByEquipmentCode;
    private final CompleteWorkOrderUseCase completeWorkOrder;
    private final UpdateWorkOrderUseCase updateWorkOrder;
    private final CancelWorkOrderUseCase cancelWorkOrder;


    @GetMapping
    public ResponseEntity<PagedResponse<OrderResponse>> getAll(@PageableDefault Pageable page) {
        return ResponseEntity.ok(findAllWorkOrder.execute(PageRequest.of(page.getPageNumber(),page.getPageSize(),null, "DESC")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(findWorkOrderById.execute(id));

    }

    @GetMapping("/search") // implementar a busca por nome do equipamento et...
    public ResponseEntity<PagedResponse<OrderResponse>> search( @PageableDefault Pageable page) {
        return ResponseEntity.ok(findAllWorkOrder.execute(PageRequest.of(page.getPageNumber(),page.getPageSize(),"createdAt", "DESC")));
    }

    @GetMapping("/search/equipment/{equipmentId}")
    public ResponseEntity<List<OrderResponse>> search(@PathVariable UUID equipmentCode){
        return ResponseEntity.ok(findAllWorkOrderByEquipmentCode.execute(equipmentCode));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> addOrder(@RequestBody @Valid CreateOrderRequest dto, Authentication authUser ) {
        return ResponseEntity.ok(createWorkOrder.execute(dto,authUser));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<OrderResponse> completeOrder(@PathVariable UUID id,@RequestBody @Valid CompleteOrderRequest dto, Authentication authUser) {
        return ResponseEntity.ok(completeWorkOrder.execute(id,dto,authUser));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable UUID id,@RequestBody @Valid CancelOrderRequest dto, Authentication authUser) {
        cancelWorkOrder.execute(id,dto,authUser);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable UUID id,@RequestBody @Valid UpdateOrderRequest dto) {
        return ResponseEntity.ok(updateWorkOrder.execute(id,dto));
    }

}
