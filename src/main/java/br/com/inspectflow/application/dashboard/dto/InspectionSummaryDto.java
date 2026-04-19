package br.com.inspectflow.application.dashboard.dto;

import java.util.List;

public record InspectionSummaryDto(
    long totalInspections,
    List<MonthlyCountDto> monthlyInspections
) {}
