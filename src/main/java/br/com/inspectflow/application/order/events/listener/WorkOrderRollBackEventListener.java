package br.com.inspectflow.application.order.events.listener;

import br.com.inspectflow.application.bucket.services.DeleteFileService;
import br.com.inspectflow.application.order.events.WorkOrderRollBackMinio;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class WorkOrderRollBackEventListener {
    private final DeleteFileService deleteFileService;


    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void onWorkOrderRollBack(WorkOrderRollBackMinio event){
            deleteFileService.deleteFile(event.fileUrl());

    }
}
