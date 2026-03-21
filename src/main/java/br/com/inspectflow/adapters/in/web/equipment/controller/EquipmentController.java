package br.com.inspectflow.adapters.in.web.equipment.controller;

import br.com.inspectflow.application.equipment.dto.*;
import br.com.inspectflow.application.equipment.services.*;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final CreateEquipmentService createEquipmentService;
    private final FindAllEquipmentService findAllEquipmentService;
    private final FindByIdEquipmentService findByIdEquipmentService;
    private final FindByEquipmentCodeService findByEquipmentCodeService;
    private final UpdateEquipmentService updateEquipmentService;
    private final UploadEquipmentAttachment uploadEquipmentAttachment;
    private final DeleteEquipmentAttachmentService deleteEquipmentAttachmentService;

    @GetMapping
    public ResponseEntity<PagedResponse<EquipmentResponse>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(
                findAllEquipmentService.execute(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()))
        );
    }

    @GetMapping("code/{code}")
    public ResponseEntity<EquipmentResponse> getByCode(@PathVariable @Valid String code) {
        return ResponseEntity.ok(findByEquipmentCodeService.execute(code));
    }


    @GetMapping("/{id}")
    public ResponseEntity<EquipmentDetailsResponse> getById(@PathVariable @Valid UUID id) {
        return ResponseEntity.ok(findByIdEquipmentService.execute(id));
    }

    @PostMapping
    public ResponseEntity<EquipmentResponse> addEquipment(@RequestBody @Valid CreateEquipmentRequest dto) {
        return ResponseEntity.ok(createEquipmentService.execute(dto));
    }

    @PostMapping(value = "/{id}/attachments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EquipmentDetailsResponse> addAttachment(
            @PathVariable UUID id,
            @ModelAttribute EquipmentAttachmentRequest dto
    ) {

        return ResponseEntity.ok(uploadEquipmentAttachment.execute(id,dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentResponse> updateEquipment(@PathVariable UUID id, @RequestBody @Valid UpdateEquipmentRequest dto) {
        return ResponseEntity.ok(updateEquipmentService.execute(id, dto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> disableEquipment() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping({"/{id}/attachments/{attachmentId}"})
    public ResponseEntity<Void> deleteAttachment(@PathVariable UUID id, @PathVariable UUID attachmentId) {
        deleteEquipmentAttachmentService.execute(id, attachmentId);
        return ResponseEntity.ok().build();
    }
}
