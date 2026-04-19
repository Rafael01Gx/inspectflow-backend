package br.com.inspectflow.application.dashboard.dto;

import java.util.List;

public record WorkOrderStatusMonthlyCountDto(
    int year,
    int month,
    List<WorkOrderStatusCountDto> statusCounts
) {}
