package br.com.inspectflow.domain.inspection.models;

import lombok.Builder;

import java.util.List;

@Builder
public record ComponentResults(
        String componentId,
        String componentName,
        List<InspectionItemResult> items
) {
}
