package br.com.inspectflow.application.dashboard.ports.in;

import br.com.inspectflow.application.dashboard.dto.PersonalActivityDto;

import java.util.List;
import java.util.UUID;

public interface PersonalActivityUseCase {
    List<PersonalActivityDto> execute(UUID userId, String groupBy);
}
