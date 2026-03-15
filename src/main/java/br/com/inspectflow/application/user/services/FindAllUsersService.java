package br.com.inspectflow.application.user.services;

import br.com.inspectflow.application.user.dto.UserResponse;
import br.com.inspectflow.application.user.ports.in.FindAllUsersUseCase;
import br.com.inspectflow.domain.common.pagination.PageRequest;
import br.com.inspectflow.domain.common.pagination.PagedResponse;
import br.com.inspectflow.domain.user.models.User;
import br.com.inspectflow.domain.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindAllUsersService implements FindAllUsersUseCase {

    private final UserRepository userRepository;

    @Override
    public PagedResponse<UserResponse> execute(PageRequest pageRequest) {
        PagedResponse<User> pagedUsers = userRepository.findAll(pageRequest);
        
        return new PagedResponse<>(
                pagedUsers.content().stream()
                        .map(user -> new UserResponse(user.getId(), user.getEmail(), user.getRole(), user.isActive()))
                        .collect(Collectors.toList()),
                pagedUsers.pageNumber(),
                pagedUsers.pageSize(),
                pagedUsers.totalElements(),
                pagedUsers.totalPages(),
                pagedUsers.isLast()
        );
    }
}
