package br.com.inspectflow.application.dashboard.dto;

public record KpiSummaryDto(
    Double mttrInDays,
    long upcomingInspectionsCount,
    Double complianceRate
) {}
