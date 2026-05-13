package br.com.inspectflow.application.dashboard.dto;

public record PersonalActivityDto(
        String period,
        long inspectionsDone,
        long approved,
        long rejected,
        long pending
) {
}
