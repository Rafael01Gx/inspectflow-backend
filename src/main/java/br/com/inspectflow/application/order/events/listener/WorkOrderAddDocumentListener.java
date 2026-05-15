package br.com.inspectflow.application.order.events.listener;

import br.com.inspectflow.application.email.dto.SendWorkOrderUpdateMailSend;
import br.com.inspectflow.application.email.ports.in.SendWorkOrderUpdateMailUseCase;
import br.com.inspectflow.application.order.events.WorkOrderAddDocumentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkOrderAddDocumentListener {
    private final SendWorkOrderUpdateMailUseCase sendWorkOrderUpdateMailUseCase;


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onWorkOrderAddDocument(WorkOrderAddDocumentEvent event) {
        try {
            sendWorkOrderUpdateMailUseCase.execute(new SendWorkOrderUpdateMailSend(
                    event.numeroOrdemServico(),
                    event.statusOrdemServico(),
                    event.equipmentName(),
                    event.tipoDocumento(),
                    event.nomeArquivo(),
                    event.assigneeEmail()
            ));
        } catch (Exception ex) {
            log.error("Falha ao enviar email para WorkOrder id={}: {}", event.numeroOrdemServico(), ex.getMessage(), ex);
        }
    }
}
