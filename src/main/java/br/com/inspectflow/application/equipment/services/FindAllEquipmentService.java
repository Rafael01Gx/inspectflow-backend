package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.equipment.ports.in.FindAllEquipmentUseCase;
import br.com.inspectflow.application.equipment.dto.EquipmentResponse;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAllEquipmentService implements FindAllEquipmentUseCase {

    private final EquipmentRepository repository;


    @Override
    public PagedResponse<EquipmentResponse> execute(PageRequest pageable) {
        var page = repository.findAll(pageable);
        return new PagedResponse<>(
                page.content().stream()
                        .map(EquipmentResponse::from)
                        .toList(),
                page.pageNumber(),
                page.pageSize(),
                page.totalElements(),
                page.totalPages(),
                page.isLast()
        );
    }
}
