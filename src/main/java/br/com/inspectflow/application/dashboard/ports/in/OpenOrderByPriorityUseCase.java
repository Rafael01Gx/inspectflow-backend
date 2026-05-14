package br.com.inspectflow.application.dashboard.ports.in;

import br.com.inspectflow.application.dashboard.dto.OpenOrderByPriorityDto;

import java.util.List;

public interface OpenOrderByPriorityUseCase {

    List<OpenOrderByPriorityDto> execute();
}
