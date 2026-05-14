package br.com.inspectflow.application.user.events;

public record RecoveryPasswordEvent(
        String email,
        String name,
        String token
) {
}
