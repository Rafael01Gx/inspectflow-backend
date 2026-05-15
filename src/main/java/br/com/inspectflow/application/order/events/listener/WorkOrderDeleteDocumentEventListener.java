package br.com.inspectflow.application.order.events.listener;

import br.com.inspectflow.application.bucket.services.DeleteFileService;
import br.com.inspectflow.application.order.events.WorkOrderDeleteDocumentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkOrderDeleteDocumentEventListener {
    private final DeleteFileService deleteFileService;


    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onDeleteAttachment(WorkOrderDeleteDocumentEvent event) {
        deleteFileService.deleteFile(event.fileUrl());
    }
}
