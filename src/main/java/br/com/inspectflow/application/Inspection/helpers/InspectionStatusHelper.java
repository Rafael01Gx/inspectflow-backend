package br.com.inspectflow.application.Inspection.helpers;

import br.com.inspectflow.application.Inspection.dto.InspectionComponentResultsRequest;
import br.com.inspectflow.application.Inspection.dto.InspectionItemResultRequest;
import br.com.inspectflow.application.Inspection.dto.InspectionRequest;
import br.com.inspectflow.application.Inspection.dto.InspectionStatusResult;
import br.com.inspectflow.domain.inspection.enums.InspectionItemStatus;
import br.com.inspectflow.domain.inspection.enums.InspectionStatus;

import java.util.ArrayList;
import java.util.List;

public class InspectionStatusHelper {


    public static InspectionStatusResult resolve(InspectionRequest dto) {
        List<String> notes = new ArrayList<>();
        boolean hasNonCriticalNok = false;

        for (InspectionComponentResultsRequest componentResult : dto.componentResults()) {
            for (InspectionItemResultRequest item : componentResult.items()) {

                if (item.status() == InspectionItemStatus.NOK && item.impedimentItem()) {
                    notes.add(componentResult.componentName().toUpperCase() + ": " + item.observation());
                    return new InspectionStatusResult(InspectionStatus.NON_CONFORMING.getValue(),notes);
                }
                if (item.status() == InspectionItemStatus.NOK) {
                    hasNonCriticalNok = true;
                }
            }
        }

        if (hasNonCriticalNok) {
            return new InspectionStatusResult(InspectionStatus.CONFORMING_WITH_OBSERVATIONS.getValue(),notes);
        }

        return new InspectionStatusResult(InspectionStatus.CONFORMING.getValue(),notes);
    }

}
