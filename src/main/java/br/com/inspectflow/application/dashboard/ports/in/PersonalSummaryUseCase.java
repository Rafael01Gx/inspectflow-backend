package br.com.inspectflow.application.dashboard.ports.in;

import br.com.inspectflow.application.dashboard.dto.PersonalSummaryDto;

import java.util.UUID;

public interface PersonalSummaryUseCase {

    PersonalSummaryDto execute(UUID userId);
}
