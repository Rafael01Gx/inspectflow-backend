package br.com.inspectflow.application.checklist.services;

import br.com.inspectflow.application.checklist.ports.in.FindChecklistByIdUseCase;
import br.com.inspectflow.application.http.handlers.CheckListNotFoundException;
import br.com.inspectflow.domain.checklist.models.Checklist;
import br.com.inspectflow.domain.checklist.repositories.CheckListRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindChecklistByIdService implements FindChecklistByIdUseCase {

    private final CheckListRepository repository;

    @Override
    @Observed(name = "checklist.find",
    contextualName = "Busca checklist por id")
    public Checklist execute(String id) {
        return repository.findById(id).orElseThrow(CheckListNotFoundException::new);
    }
}
