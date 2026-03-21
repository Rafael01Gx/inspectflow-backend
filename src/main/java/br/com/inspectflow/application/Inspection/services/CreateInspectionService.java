package br.com.inspectflow.application.Inspection.services;

import br.com.inspectflow.application.Inspection.dto.InspectionRequest;
import br.com.inspectflow.application.Inspection.ports.in.CreateInspectionUseCase;
import br.com.inspectflow.application.http.handlers.EquipmentNotFoundException;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import br.com.inspectflow.domain.inspection.models.Inspection;
import br.com.inspectflow.domain.inspection.repositories.InspectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateInspectionService implements CreateInspectionUseCase {

    private final InspectionRepository inspectionRepository;
    private final EquipmentRepository equipmentRepository;

    @Override
    @Transactional
    public Inspection execute(InspectionRequest dto, Authentication user) {
        Equipment equipment = equipmentRepository.findById(dto.equipmentId()).orElseThrow(EquipmentNotFoundException::new);


        equipment.updateInspection();



        return null;
    }



}
