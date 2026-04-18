package br.com.inspectflow.application.Inspection.services;

import br.com.inspectflow.application.Inspection.dto.InspectionRequest;
import br.com.inspectflow.application.Inspection.helpers.InspectionStatusHelper;
import br.com.inspectflow.application.Inspection.mappers.InspectionMapper;
import br.com.inspectflow.application.Inspection.ports.in.CreateInspectionUseCase;
import br.com.inspectflow.application.Inspection.validators.QualifiedProfessionalValidator;
import br.com.inspectflow.application.http.handlers.EquipmentNotFoundException;
import br.com.inspectflow.application.http.handlers.UserNotFoundException;
import br.com.inspectflow.application.order.ports.in.CreateSystemWorkOrderUseCase;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.equipment.repositories.EquipmentRepository;
import br.com.inspectflow.domain.inspection.enums.InspectionStatus;
import br.com.inspectflow.domain.inspection.models.Inspection;
import br.com.inspectflow.domain.inspection.models.InspectionHistory;
import br.com.inspectflow.domain.inspection.repositories.InspectionHistoryRepository;
import br.com.inspectflow.domain.inspection.repositories.InspectionRepository;
import br.com.inspectflow.domain.order.repositories.WorkOrderRepository;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateInspectionService implements CreateInspectionUseCase {

    private final InspectionRepository inspectionRepository;
    private final InspectionHistoryRepository inspectionHistoryRepository;
    private final EquipmentRepository equipmentRepository;
    private final QualifiedProfessionalValidator  qualifiedProfessionalValidator;
    private final UserRepository userRepository;
    private final InspectionMapper inspectionMapper;
    private final CreateSystemWorkOrderUseCase createSystemWorkOrderUseCase;


    @Override
    @Transactional
    public Inspection execute(InspectionRequest dto, Authentication auth) {

        Equipment equipment = equipmentRepository.findById(dto.equipmentId()).orElseThrow(EquipmentNotFoundException::new);

        User user = userRepository.findByEmail(auth.getName()).orElseThrow(UserNotFoundException::new);

        qualifiedProfessionalValidator.validate(user,dto);

        Inspection inspection = inspectionMapper.fromRequest(equipment,dto,user);

        var statusResult = InspectionStatusHelper.resolve(dto);
        inspection.setStatus(statusResult.status());
        inspectionRepository.save(inspection);

        equipment.updateInspection();

        InspectionHistory historico = InspectionHistory.builder()
                .inspectionId(inspection.getId())
                .equipmentId(equipment.getId())
                .inspectorId(user.getId())
                .inspectorName(user.getName())
                .date(inspection.getDate())
                .category(inspection.getInspectionCategory())
                .status(inspection.getStatus())
                .build();

        inspectionHistoryRepository.save(historico);

       if (inspection.getStatus().equals(InspectionStatus.NON_CONFORMING.getValue())){
           createSystemWorkOrderUseCase.execute(user,equipment,statusResult.notes() );
       }


        return inspection;
    }


}
