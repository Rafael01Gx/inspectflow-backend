package br.com.inspectflow.adapters.in.web.order.controller;

import br.com.inspectflow.adapters.in.helpers.ExtractUserId;
import br.com.inspectflow.adapters.in.mappers.PageableRequestMapper;
import br.com.inspectflow.application.order.dto.*;
import br.com.inspectflow.application.order.ports.in.*;
import br.com.inspectflow.application.order.services.DeleteOrderAttachmentService;
import br.com.inspectflow.application.stock.ports.in.FindAllWorkOrderByEquipmentCodeUseCase;
import br.com.inspectflow.domain.bucket.dto.DownloadResponse;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateWorkOrderUseCase createWorkOrder;
    private final FindAllWorkOrderUseCase findAllWorkOrder;
    private final FindWorkOrderByIdUseCase findWorkOrderById;
    private final FindAllByAssigneeUseCase findAllWorkOrderByUser;
    private final SearchWorkOrderUseCase searchWorkOrder;
    private final SetAssigneeWorkOrderUseCase setAssignee;
    private final FindAllWorkOrderByEquipmentCodeUseCase findAllWorkOrderByEquipmentCode;
    private final CompleteWorkOrderUseCase completeWorkOrder;
    private final UpdateWorkOrderUseCase updateWorkOrder;
    private final CancelWorkOrderUseCase cancelWorkOrder;
    private final UploadOrderAttachmentUseCase uploadOrderAttachmentUseCase;
    private final DownloadOrderDocumentUseCase downloadFileService;
    private final DeleteOrderAttachmentService deleteOrderAttachmentService;


    @GetMapping
    public ResponseEntity<PagedResponse<OrderResponse>> getAll(@PageableDefault Pageable page) {
        return ResponseEntity.ok(findAllWorkOrder.execute(PageableRequestMapper.fromRequest(page)));
    }

    @PreAuthorize("hasRole('LIDER')")
    @GetMapping("/all")
    public ResponseEntity<List<OrderListAllResponse>> getAllList() {
        return ResponseEntity.ok(findAllWorkOrder.execute());
    }

    @PreAuthorize("hasRole('LIDER')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(findWorkOrderById.execute(id));

    }

    @GetMapping("/my-orders")
    public ResponseEntity<PagedResponse<OrderResponse>> searchMyOrders( Pageable pageable, Authentication authUser) {
        return ResponseEntity.ok(findAllWorkOrderByUser.execute(authUser,PageableRequestMapper.fromRequest(pageable)));
    }

    @PreAuthorize("hasRole('LIDER')")
    @GetMapping("/search")
    public ResponseEntity<PagedResponse<OrderResponse>> search(@ModelAttribute SearchOrderFilterRequest filter,
                                                        Pageable pageable) {
        return ResponseEntity.ok(searchWorkOrder.execute(filter,PageableRequestMapper.fromRequest(pageable)));
    }

    @GetMapping("/search/equipment/{equipmentId}")
    public ResponseEntity<List<OrderResponse>> search(@PathVariable UUID equipmentId){
        return ResponseEntity.ok(findAllWorkOrderByEquipmentCode.execute(equipmentId));
    }
    @GetMapping("/document/{id}/download")
    public ResponseEntity<StreamingResponseBody> downloadDocument(@PathVariable UUID id) {
        DownloadResponse req = downloadFileService.execute(id);

        StreamingResponseBody responseBody = outputStream -> {
            try (var fileStream = req.file()) {
                fileStream.transferTo(outputStream);
            }
        };

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + req.filename() + "\"")
                .contentType(MediaType.parseMediaType(req.contentType()))
                .body(responseBody);

    }

    @GetMapping("/document/{id}/view")
    public ResponseEntity<StreamingResponseBody> viewDocument(@PathVariable UUID id) {
        DownloadResponse req = downloadFileService.execute(id);

        StreamingResponseBody responseBody = outputStream -> {
            try (var fileStream = req.file()) {
                fileStream.transferTo(outputStream);
            }
        };

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + req.filename() + "\"")
                .contentType(MediaType.parseMediaType(req.contentType()))
                .body(responseBody);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> addOrder(@RequestBody @Valid CreateOrderRequest dto, Authentication authUser ) {
        return ResponseEntity.ok(createWorkOrder.execute(dto,authUser));
    }

    @PreAuthorize("hasRole('LIDER')")
    @PostMapping("{id}/set-assignee/{assigneeId}")
    public ResponseEntity<Void> setAssignee(@PathVariable UUID id, @PathVariable UUID assigneeId) {
        setAssignee.execute(id,assigneeId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "{id}/document",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OrderDetailResponse> uploadDocument(@PathVariable UUID id, @ModelAttribute OrderAttachmentRequest dto, Authentication authUser){
        UUID userId= ExtractUserId.fromAuthentication(authUser);
        return ResponseEntity.ok().body(uploadOrderAttachmentUseCase.execute(id,userId,dto));
    }


    @PostMapping("/{id}/complete")
    public ResponseEntity<OrderResponse> completeOrder(@PathVariable UUID id,@RequestBody @Valid CompleteOrderRequest dto, Authentication authUser) {
        return ResponseEntity.ok(completeWorkOrder.execute(id,dto,authUser));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable UUID id,@RequestBody @Valid CancelOrderRequest dto, Authentication authUser) {
        cancelWorkOrder.execute(id,dto,authUser);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable UUID id,@RequestBody @Valid UpdateOrderRequest dto, Authentication authUser) {
        return ResponseEntity.ok(updateWorkOrder.execute(id,dto, authUser));
    }

    @DeleteMapping("{id}/document/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable UUID id, @PathVariable UUID documentId){
        deleteOrderAttachmentService.execute(id,documentId);
        return ResponseEntity.ok().build();
    }




}
