package br.com.inspectflow.adapters.in.web.Inspection.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inspections")
public class InspectionController {

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> addInspection() {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInspection() {
        return ResponseEntity.ok().build();
    }


}
