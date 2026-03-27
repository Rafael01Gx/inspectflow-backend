package br.com.inspectflow.application.Inspection.dto;

import java.util.List;

public record InspectionStatusResult(
        String status,
        List<String> notes
) {
}
