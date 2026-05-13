package br.com.inspectflow.application.dashboard.dto;

public record PersonalWorkOrderTimelineDto(
        String day,
        long completed,
        long inProgress,
        long pending
) {
}
