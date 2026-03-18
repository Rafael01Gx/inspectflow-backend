package br.com.inspectflow.adapters.in.web.checklist.controller;

import br.com.inspectflow.application.checklist.services.FindAllCheckListService;
import br.com.inspectflow.application.checklist.services.FindByIdCheclistService;
import br.com.inspectflow.domain.checklist.models.Checklist;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checklists")
@RequiredArgsConstructor
public class CheckListController {

    private final FindAllCheckListService findAllCheckListService;
    private final FindByIdCheclistService findByIdCheclistService;

    @GetMapping
    public ResponseEntity<PagedResponse<Checklist>> getAll(@PageableDefault Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(findAllCheckListService.execute(pageRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Checklist> getById(@PathVariable String id) {
        return ResponseEntity.ok(findByIdCheclistService.execute(id));
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
