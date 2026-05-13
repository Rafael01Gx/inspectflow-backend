package br.com.inspectflow.application.dashboard.services;

import br.com.inspectflow.application.dashboard.dto.PersonalActivityDto;
import br.com.inspectflow.application.dashboard.dto.PersonalActivityResponse;
import br.com.inspectflow.application.dashboard.ports.in.PersonalActivityUseCase;
import br.com.inspectflow.application.dashboard.ports.out.PersonalDashboardQueryRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalActivityService implements PersonalActivityUseCase {

    private final PersonalDashboardQueryRepository repository;

    @Override
    //@Cacheable(value = "personalActivity", key = "#userId + '-' + #groupBy")
    @Transactional(readOnly = true)
    @Observed(name = "dashboard.personal.activity", contextualName = "personal activity timeline")
    public PersonalActivityResponse execute(UUID userId, String groupBy) {
        validateGroupBy(groupBy);
        return new PersonalActivityResponse(
                new ArrayList<>(repository.findActivityByPeriod(userId, groupBy))

        );
    }

    private void validateGroupBy(String groupBy) {
        if (!List.of("day", "week", "month").contains(groupBy.toLowerCase())) {
            throw new IllegalArgumentException("groupBy deve ser 'day', 'week' ou 'month'. Recebido: " + groupBy);
        }
    }
}
