package br.com.inspectflow.application.dashboard.ports.in;

import br.com.inspectflow.application.dashboard.dto.PersonalActivityResponse;

import java.util.UUID;

public interface PersonalActivityUseCase {
    PersonalActivityResponse execute(UUID userId, String groupBy);
}
