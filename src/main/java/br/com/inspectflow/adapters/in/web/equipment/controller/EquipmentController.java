package br.com.inspectflow.adapters.in.web.equipment.controller;

import br.com.inspectflow.application.equipment.dto.CreateEquipmentRequest;
import br.com.inspectflow.application.equipment.services.CreateEquipmentService;
import br.com.inspectflow.application.equipment.services.FindAllEquipmentService;
import br.com.inspectflow.application.equipment.services.FindByEquipmentCodeService;
import br.com.inspectflow.application.equipment.services.FindByIdEquipmentService;
import br.com.inspectflow.application.equipment_component.dto.EquipmentResponse;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public ResponseEntity<PagedResponse<EquipmentResponse>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(findAllEquipmentService.execute(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize())));
    }

    @GetMapping("code/{code}")
    public ResponseEntity<EquipmentResponse> getByCode(@PathVariable @Valid String code) {
        return ResponseEntity.ok(findByEquipmentCodeService.execute(code));
    }
    @GetMapping("/{id}")
    public ResponseEntity<EquipmentResponse> getById(@PathVariable @Valid UUID id) {
        return ResponseEntity.ok(findByIdEquipmentService.execute(id));
    }

    @PostMapping
    public ResponseEntity<EquipmentResponse> addEquipment(@RequestBody @Valid CreateEquipmentRequest dto) {
        return ResponseEntity.ok(createEquipmentService.execute(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEquipment() {
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{id}")
    public ResponseEntity<?> disableEquipment() {
        return ResponseEntity.ok().build();
    }
}
