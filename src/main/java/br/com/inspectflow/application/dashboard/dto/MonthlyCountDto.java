package br.com.inspectflow.application.dashboard.dto;

public record MonthlyCountDto(
    int year,
    int month,
    long count
) {}
