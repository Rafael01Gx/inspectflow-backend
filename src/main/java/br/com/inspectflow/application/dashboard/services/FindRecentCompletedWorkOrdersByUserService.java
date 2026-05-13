package br.com.inspectflow.application.dashboard.services;

import br.com.inspectflow.application.dashboard.dto.PersonalWorkOrderSummaryDto;
import br.com.inspectflow.application.dashboard.ports.in.FindRecentCompletedWorkOrdersByUserUseCase;
import br.com.inspectflow.application.dashboard.ports.out.PersonalDashboardQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindRecentCompletedWorkOrdersByUserService implements FindRecentCompletedWorkOrdersByUserUseCase {
    private final PersonalDashboardQueryRepository repository;

    @Override
    //@Cacheable(value = "personalRecentCompleted", key = "#userId + '-' + #limit")
    @Transactional(readOnly = true)
    public List<PersonalWorkOrderSummaryDto> execute(UUID userId, int limit) {
        return repository.findRecentCompletedWorkOrders(userId, limit);
    }
}
