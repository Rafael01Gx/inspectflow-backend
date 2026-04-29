package br.com.inspectflow.adapters.in.web.order.controller;

import br.com.inspectflow.adapters.in.mappers.PageableRequestMapper;
import br.com.inspectflow.application.order.dto.*;
import br.com.inspectflow.application.order.ports.in.*;
import br.com.inspectflow.application.stock.ports.in.FindAllWorkOrderByEquipmentCodeUseCase;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final SearchWorkOrderUseCase searchWorkOrder;
    private final FindAllWorkOrderByEquipmentCodeUseCase findAllWorkOrderByEquipmentCode;
    private final CompleteWorkOrderUseCase completeWorkOrder;
    private final UpdateWorkOrderUseCase updateWorkOrder;
    private final CancelWorkOrderUseCase cancelWorkOrder;


    @GetMapping
    public ResponseEntity<PagedResponse<OrderResponse>> getAll(@PageableDefault Pageable page) {
        return ResponseEntity.ok(findAllWorkOrder.execute(PageableRequestMapper.fromRequest(page)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(findWorkOrderById.execute(id));

    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/search")
    public ResponseEntity<PagedResponse<OrderResponse>> search(  @ModelAttribute SearchOrderFilterRequest filter,
                                                        Pageable pageable) {
        return ResponseEntity.ok(searchWorkOrder.execute(filter,PageableRequestMapper.fromRequest(pageable)));
    }

    @GetMapping("/search/equipment/{equipmentId}")
    public ResponseEntity<List<OrderResponse>> search(@PathVariable UUID equipmentId){
        return ResponseEntity.ok(findAllWorkOrderByEquipmentCode.execute(equipmentId));
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
