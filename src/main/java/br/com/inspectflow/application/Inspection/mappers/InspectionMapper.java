package br.com.inspectflow.application.Inspection.mappers;

import br.com.inspectflow.application.Inspection.dto.InspectionComponentResultsRequest;
import br.com.inspectflow.application.Inspection.dto.InspectionItemResultRequest;
import br.com.inspectflow.application.Inspection.dto.InspectionRequest;
import br.com.inspectflow.application.Inspection.helpers.InspectionStatusHelper;
import br.com.inspectflow.domain.equipment.models.Equipment;
import br.com.inspectflow.domain.inspection.enums.InspectionCategory;
import br.com.inspectflow.domain.inspection.models.ComponentResults;
import br.com.inspectflow.domain.inspection.models.Inspection;
import br.com.inspectflow.domain.inspection.models.InspectionItemResult;
import br.com.inspectflow.domain.user.models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InspectionMapper {


    public Inspection fromRequest(Equipment equipment, InspectionRequest dto, User user){
        return Inspection.builder()
                .equipmentId(equipment.getId().toString())
                .equipmentName(equipment.getName())
                .componentResults(dto.componentResults().stream().map(this::fromComponentResultsRequest).toList())
                .inspectionCategory(
                        switch (user.getRole()){
                            case ELETRICISTA -> InspectionCategory.ELETRICA;
                            case MECANICO -> InspectionCategory.MECANICA;
                            default -> InspectionCategory.INSPECAO;
                        }
                )
                .date(LocalDateTime.now())
                .status(InspectionStatusHelper.resolve(dto).getValue())
                .technician(user.getName())
                .technicianId(user.getId().toString())
                .notes(dto.notes())
                .build();
    }



    private ComponentResults fromComponentResultsRequest(InspectionComponentResultsRequest dto){
        return ComponentResults.builder()
                .componentId(dto.componentId().toString())
                .componentName(dto.componentName())
                .items(dto.items().stream().map(this::fromInspectionItemResultsRequest).toList())
                .build();
    }

    private InspectionItemResult fromInspectionItemResultsRequest(InspectionItemResultRequest dto){
        return InspectionItemResult.builder()
                .title(dto.title())
                .description(dto.description())
                .status(dto.status())
                .impedimentItem(dto.impedimentItem())
                .observation(dto.observation())
                .build();
    }
}
