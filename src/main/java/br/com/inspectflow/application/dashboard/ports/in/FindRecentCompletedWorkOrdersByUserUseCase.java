package br.com.inspectflow.application.dashboard.ports.in;

import br.com.inspectflow.application.dashboard.dto.PersonalWorkOrderSummaryDto;

import java.util.List;
import java.util.UUID;

public interface FindRecentCompletedWorkOrdersByUserUseCase {

    List<PersonalWorkOrderSummaryDto> execute(UUID userId, int safeLimit);
}
