package br.com.inspectflow.application.user.ports.in;

import br.com.inspectflow.application.user.dto.UserResponse;

import java.util.List;

public interface SearchByNameUseCase {
    List<UserResponse> execute(String name);
}
