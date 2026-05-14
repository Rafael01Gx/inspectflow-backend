package br.com.inspectflow.application.dashboard.dto;

public record PersonalActivityDto(
        String period,
        long inspectionsDone,
        long conforming,
        long conformingWithObservations,
        long nonConforming
) {
}
