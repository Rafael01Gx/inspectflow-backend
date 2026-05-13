package br.com.inspectflow.application.dashboard.dto;

import java.util.List;

public record PersonalActivityResponse(
        List<PersonalActivityDto> activities
) {
}