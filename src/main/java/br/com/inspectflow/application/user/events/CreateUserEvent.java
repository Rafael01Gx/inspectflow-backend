package br.com.inspectflow.application.user.events;

public record CreateUserEvent(
        String email,
        String name,
        String password

) {

}
