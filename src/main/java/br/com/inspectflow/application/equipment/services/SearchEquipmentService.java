package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.equipment.dto.EquipmentResponse;
import br.com.inspectflow.application.equipment.ports.in.SearchEquipmentUseCase;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchEquipmentService implements SearchEquipmentUseCase {
    private final EquipmentRepository equipmentRepository;

    @Override
    public List<EquipmentResponse> execute(String q) {
        return equipmentRepository.findTop10ByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(q, q).stream().map(EquipmentResponse::from).toList();
    }
}
