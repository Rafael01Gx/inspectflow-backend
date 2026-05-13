package br.com.inspectflow.application.dashboard.services;

import br.com.inspectflow.application.dashboard.dto.PersonalSummaryDto;
import br.com.inspectflow.application.dashboard.ports.in.PersonalSummaryUseCase;
import br.com.inspectflow.application.dashboard.ports.out.PersonalDashboardQueryRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalSummaryService implements PersonalSummaryUseCase {

    private final PersonalDashboardQueryRepository repository;

    @Override
    @Cacheable(value = "personalSummary", key = "#userId")
    @Transactional(readOnly = true)
    @Observed(name = "dashboard.personal.summary", contextualName = "personal summary")
    public PersonalSummaryDto execute(UUID userId) {
        return repository.findPersonalSummary(userId);
    }
}
