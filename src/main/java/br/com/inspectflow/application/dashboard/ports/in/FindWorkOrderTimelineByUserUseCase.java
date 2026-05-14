package br.com.inspectflow.application.dashboard.ports.in;

import br.com.inspectflow.application.dashboard.dto.PersonalWorkOrderTimelineDto;

import java.util.List;
import java.util.UUID;

public interface FindWorkOrderTimelineByUserUseCase {
    List<PersonalWorkOrderTimelineDto> execute(UUID userId, int months);
}
