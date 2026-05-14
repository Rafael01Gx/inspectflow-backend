package br.com.inspectflow.application.dashboard.services;

import br.com.inspectflow.application.dashboard.dto.PersonalWorkOrderTimelineDto;
import br.com.inspectflow.application.dashboard.ports.in.FindWorkOrderTimelineByUserUseCase;
import br.com.inspectflow.application.dashboard.ports.out.PersonalDashboardQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindWorkOrderTimelineByUserService implements FindWorkOrderTimelineByUserUseCase {

    private final PersonalDashboardQueryRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<PersonalWorkOrderTimelineDto> execute(UUID userId, int months) {
        return repository.findWorkOrderTimeline(userId, months);
    }
}
