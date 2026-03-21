package br.com.inspectflow.adapters.in.web.Inspection.controller;

import br.com.inspectflow.application.Inspection.dto.InspectionRequest;
import br.com.inspectflow.application.Inspection.ports.in.CreateInspectionUseCase;
import com.sun.security.auth.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inspections")
@RequiredArgsConstructor
public class InspectionController {

    private final CreateInspectionUseCase createInspection;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> addInspection(@RequestBody @Valid InspectionRequest dto, Authentication user) {
        return ResponseEntity.ok(createInspection.execute(dto,user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInspection() {
        return ResponseEntity.ok().build();
    }


}
