package br.com.inspectflow.adapters.in.web.Inspection.controller;

import br.com.inspectflow.application.Inspection.dto.InspectionRequest;
import br.com.inspectflow.application.Inspection.ports.in.CreateInspectionUseCase;
import br.com.inspectflow.application.Inspection.ports.in.FindByEquipmentIdUseCase;
import br.com.inspectflow.application.Inspection.ports.in.FindInspectionByIdUseCase;
import br.com.inspectflow.domain.inspection.models.InspectionHistory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/inspections")
@RequiredArgsConstructor
public class InspectionController {

    private final CreateInspectionUseCase createInspection;
    private final FindByEquipmentIdUseCase findByEquipmentId;
    private final FindInspectionByIdUseCase findInspectionById;


    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().build();
    }


    @GetMapping("equipment/{equipmentId}/history")
    public  ResponseEntity<List<InspectionHistory>> getHistory(@PathVariable UUID equipmentId) {
        return ResponseEntity.ok(findByEquipmentId.execute(equipmentId));
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(findInspectionById.execute(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ELETRICISTA', 'ROLE_MECANICO', 'ROLE_ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<?> addInspection(@RequestBody @Valid InspectionRequest dto, Authentication user) {

        return ResponseEntity.ok(createInspection.execute(dto,user));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ELETRICISTA', 'ROLE_MECANICO', 'ROLE_ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateInspection() {
        return ResponseEntity.ok().build();
    }


}
