package br.com.inspectflow.application.Inspection.helpers;

import br.com.inspectflow.application.Inspection.dto.InspectionComponentResultsRequest;
import br.com.inspectflow.application.Inspection.dto.InspectionItemResultRequest;
import br.com.inspectflow.application.Inspection.dto.InspectionRequest;
import br.com.inspectflow.domain.inspection.enums.InspectionItemStatus;
import br.com.inspectflow.domain.inspection.enums.InspectionStatus;

public class InspectionStatusHelper {
    public static InspectionStatus resolve(InspectionRequest dto) {
        boolean hasNonCriticalNok = false;

        for (InspectionComponentResultsRequest componentResult : dto.componentResults()) {
            for (InspectionItemResultRequest item : componentResult.items()) {

                if (item.status() == InspectionItemStatus.NOK && item.impedimentItem()) {
                    return InspectionStatus.NON_CONFORMING;
                }
                if (item.status() == InspectionItemStatus.NOK) {
                    hasNonCriticalNok = true;
                }
            }
        }

        if (hasNonCriticalNok) {
            return InspectionStatus.CONFORMING_WITH_OBSERVATIONS;
        }

        return InspectionStatus.CONFORMING;
    }

}
