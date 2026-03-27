package br.com.inspectflow.adapters.in.web.order.controller;

import br.com.inspectflow.application.order.dto.*;
import br.com.inspectflow.application.order.ports.in.*;
import br.com.inspectflow.application.user.services.SecurityUser;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateWorkOrderUseCase createWorkOrder;
    private final FindAllWorkOrderUseCase findAllWorkOrder;
    private final FindWorkOrderByIdUseCase findWorkOrderById;
    private final CompleteWorkOrderUseCase completeWorkOrder;
    private final UpdateWorkOrderUseCase updateWorkOrder;
    private final CancelWorkOrderUseCase cancelWorkOrder;


    @GetMapping
    public ResponseEntity<PagedResponse<OrderResponse>> getAll(@PageableDefault Pageable page) {
        return ResponseEntity.ok(findAllWorkOrder.execute(PageRequest.of(page.getPageNumber(),page.getPageSize())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(findWorkOrderById.execute(id));

    }

    @PostMapping
    public ResponseEntity<OrderResponse> addOrder(@RequestBody @Valid CreateOrderRequest dto, Authentication authentication ) {
        var user = (SecurityUser) authentication.getPrincipal();
        return ResponseEntity.ok(createWorkOrder.execute(user.getId(),dto));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<OrderResponse> completeOrder(@PathVariable UUID id,@RequestBody @Valid CompleteOrderRequest dto) {
        return ResponseEntity.ok(completeWorkOrder.execute(id,dto));
    }


    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable UUID id,@RequestBody @Valid UpdateOrderRequest dto) {
        return ResponseEntity.ok(updateWorkOrder.execute(id,dto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable UUID id,@RequestBody @Valid CancelOrderRequest dto) {
        cancelWorkOrder.execute(id,dto);
        return ResponseEntity.ok().build();
    }
}
