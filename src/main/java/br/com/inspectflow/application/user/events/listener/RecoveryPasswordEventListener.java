package br.com.inspectflow.application.user.events.listener;

import br.com.inspectflow.application.email.ports.in.SendRecoveryMailUseCase;
import br.com.inspectflow.application.user.events.RecoveryPasswordEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecoveryPasswordEventListener {
    private final SendRecoveryMailUseCase recoveryEmailService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserCreated(RecoveryPasswordEvent event) {
        try {
            recoveryEmailService.execute(event.email(), event.name(), event.token());
        }
        catch (Exception ex) {
            log.error("Falha ao enviar email para usuário id={}: {}", event.email(), ex.getMessage(), ex);
        }
    }
}
