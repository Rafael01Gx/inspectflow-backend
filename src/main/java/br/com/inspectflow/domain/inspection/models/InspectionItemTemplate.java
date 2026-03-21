package br.com.inspectflow.domain.inspection.models;

import br.com.inspectflow.domain.inspection.enums.InspectionItemStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class InspectionItemTemplate extends  InspectionItem
{

    @Enumerated(EnumType.STRING)
    private InspectionItemStatus status;

    private String observation;
}
