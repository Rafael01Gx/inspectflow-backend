package br.com.inspectflow.application.dashboard.ports.in;

import br.com.inspectflow.application.dashboard.dto.PersonalDashboardFullDto;

import java.util.UUID;

public interface FullPersonalDashboardUseCase {

    PersonalDashboardFullDto execute(UUID userId, int months);
}
