package br.com.inspectflow.adapters.in.web.equipment.controller;

import br.com.inspectflow.application.equipment.dto.*;
import br.com.inspectflow.application.equipment.ports.in.*;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final CreateEquipmentUseCase createEquipmentService;
    private final FindAllEquipmentUseCase findAllEquipmentService;
    private final FindByIdEquipmentUseCase findByIdEquipmentService;
    private final FindByEquipmentCodeUseCase findByEquipmentCodeService;
    private final UpdateEquipmentUseCase updateEquipmentService;
    private final UploadEquipmentAttachmentUseCase uploadEquipmentAttachment;
    private final SearchEquipmentUseCase searchEquipmentService;
    private final DeleteEquipmentAttachmentUseCase deleteEquipmentAttachmentService;

    @GetMapping
    public ResponseEntity<PagedResponse<EquipmentResponse>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(
                findAllEquipmentService.execute(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),null, "DESC"))
        );
    }

    @GetMapping("code/{code}")
    public ResponseEntity<EquipmentResponse> getByCode(@PathVariable @Valid String code) {
        return ResponseEntity.ok(findByEquipmentCodeService.execute(code));
    }

    @GetMapping("search")
    public ResponseEntity<List<EquipmentResponse>> search(@RequestParam String q) {
        return ResponseEntity.ok(
                searchEquipmentService.execute(q)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentDetailsResponse> getById(@PathVariable @Valid UUID id) {
        return ResponseEntity.ok(findByIdEquipmentService.execute(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EquipmentResponse> addEquipment(@RequestPart("equipment") @Valid CreateEquipmentRequest dto,@RequestPart(value = "file", required = false) MultipartFile file) {
        return ResponseEntity.ok(createEquipmentService.execute(dto,file));
    }

    @PostMapping(value = "/{id}/attachments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EquipmentDetailsResponse> addAttachment(
            @PathVariable UUID id,
            @ModelAttribute EquipmentAttachmentRequest dto
    ) {

        return ResponseEntity.ok(uploadEquipmentAttachment.execute(id,dto));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EquipmentResponse> updateEquipment(@PathVariable UUID id, @RequestPart("equipment") @Valid UpdateEquipmentRequest dto,@RequestPart(value = "file", required = false) MultipartFile file) {
        return ResponseEntity.ok(updateEquipmentService.execute(id, dto,file));
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
