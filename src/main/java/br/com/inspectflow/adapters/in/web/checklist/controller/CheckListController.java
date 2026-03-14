package br.com.inspectflow.adapters.in.web.checklist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checklists")
public class CheckListController {

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> addChecklist(){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateChecklist(){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChecklist(){
        return ResponseEntity.ok().build();
    }

}
