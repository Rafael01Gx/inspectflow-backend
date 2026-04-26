package br.com.inspectflow.application.equipment.services;

import br.com.inspectflow.application.bucket.ports.in.CreatePresignedUrlUseCase;
import br.com.inspectflow.application.equipment.dto.EquipmentDetailsResponse;
import br.com.inspectflow.application.equipment.ports.in.FindByEquipmentCodeUseCase;
import br.com.inspectflow.application.http.handlers.EquipmentNotFoundException;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindByEquipmentCodeService implements FindByEquipmentCodeUseCase {
    private final EquipmentRepository equipmentRepository;
    private final CreatePresignedUrlUseCase presignedUrl;

    @Override
    public EquipmentDetailsResponse execute(String code) {
        var equipment = equipmentRepository.findByCode(code).orElseThrow(EquipmentNotFoundException::new);

        if (equipment.getImageUrl() != null && !equipment.getImageUrl().isEmpty()) {
            var url = presignedUrl.execute(equipment.getImageUrl());
            equipment.setImageUrl(url);
        }

        return EquipmentDetailsResponse.from(equipment);
    }
}
