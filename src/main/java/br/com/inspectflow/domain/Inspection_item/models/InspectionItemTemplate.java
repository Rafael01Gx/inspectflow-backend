package br.com.inspectflow.domain.Inspection_item.models;

import br.com.inspectflow.domain.Inspection_item.enums.InspectionStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class InspectionItemTemplate extends  InspectionItem
{

    @Enumerated(EnumType.STRING)
    private InspectionStatus status;

    private String observation;
}
