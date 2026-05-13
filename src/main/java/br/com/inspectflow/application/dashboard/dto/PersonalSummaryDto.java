package br.com.inspectflow.application.dashboard.dto;

public record PersonalSummaryDto (
        long openWorkOrders,
        long completedToday,
        long completedThisMonth,
        long overdueWorkOrders,
        long inspectionsThisMonth,
        long inspectionsThisWeek,
        double personalComplianceRate
){
}
