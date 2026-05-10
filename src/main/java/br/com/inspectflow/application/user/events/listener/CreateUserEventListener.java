package br.com.inspectflow.application.user.events.listener;

import br.com.inspectflow.application.email.ports.in.FirstAccessMailUseCase;
import br.com.inspectflow.application.user.events.CreateUserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateUserEventListener {
    private final FirstAccessMailUseCase firstAccessMail;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserCreated(CreateUserEvent event) {
        try {
            firstAccessMail.execute(event.email(), event.name(), event.password());
        }
        catch (Exception ex) {
            log.error("Falha ao enviar email para usuário id={}: {}", event.email(), ex.getMessage(), ex);
        }
    }
}
