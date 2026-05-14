package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.equipment.dto.EquipmentSummaryResponse;
import br.com.inspectflow.application.equipment.ports.in.FindAllEquipmentUseCase;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindAllEquipmentService implements FindAllEquipmentUseCase {

    private final EquipmentRepository repository;


    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "findAllEquipmentList", key = "#pageable.page.toString()")
    @Observed(name = "equipment.list",
            contextualName = "Lista equipamentos")
    public PagedResponse<EquipmentSummaryResponse> execute(PageRequest pageable) {
        return repository.findAll(pageable);
    }
}
