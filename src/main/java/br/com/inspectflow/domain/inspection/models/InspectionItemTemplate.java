package br.com.inspectflow.domain.inspection.models;

import br.com.inspectflow.domain.inspection.enums.InspectionStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class InspectionItemTemplate extends  InspectionItem
{

    @Enumerated(EnumType.STRING)
    private InspectionStatus status;

    private String observation;
}
