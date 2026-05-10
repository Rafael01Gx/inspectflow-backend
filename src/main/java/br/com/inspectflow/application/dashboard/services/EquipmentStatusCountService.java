package br.com.inspectflow.application.dashboard.services;

import br.com.inspectflow.application.dashboard.dto.EquipmentStatusCountDto;
import br.com.inspectflow.application.dashboard.ports.in.EquipmentStatusCountUseCase;
import br.com.inspectflow.domain.equipment.enums.EquipmentStatus;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentStatusCountService implements EquipmentStatusCountUseCase {
    private final EquipmentRepository equipmentRepository;

    @Override
    @Cacheable(value = "dashboardEquipments", key = "'statusCounts'")
    @Transactional(readOnly = true)
    @Observed(name = "dashboard.equipment-count",
    contextualName = "contagem de equipamentos por status")
    public List<EquipmentStatusCountDto> execute() {
        return equipmentRepository.countEquipmentsByStatus().stream()
                .map(result -> {
                    EquipmentStatus status = (result[0] instanceof EquipmentStatus) ?
                            (EquipmentStatus) result[0] : EquipmentStatus.valueOf(result[0].toString());
                    return new EquipmentStatusCountDto(status, ((Number) result[1]).longValue());
                })
                .collect(Collectors.toList());
    }
}
