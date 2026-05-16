package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.equipment.dto.EquipmentListResponse;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllEquipmentService implements FindAllEquipmentUseCase {

    private final EquipmentRepository repository;


    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "findPageEquipmentList", key = "':p:' + #pageable.page.toString() + ':s:' + #pageable.size.toString()")
    @Observed(name = "equipment.page-list",
            contextualName = "Lista equipamentos")
    public PagedResponse<EquipmentSummaryResponse> execute(PageRequest pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Cacheable(value = "findAllEquipmentList", key = "'list'")
    @Observed(name = "equipment.list-all",
            contextualName = "Lista completa equipamentos")
    @Transactional(readOnly = true)
    public List<EquipmentListResponse> execute() {
        return repository.findAll();
    }
}
