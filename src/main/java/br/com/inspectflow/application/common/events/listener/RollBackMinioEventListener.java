package br.com.inspectflow.application.common.events.listener;

import br.com.inspectflow.application.bucket.services.DeleteFileService;
import br.com.inspectflow.application.common.events.RollBackMinio;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class RollBackMinioEventListener {
    private final DeleteFileService deleteFileService;


    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void onWorkOrderRollBack(RollBackMinio event){
            deleteFileService.deleteFile(event.fileUrl());

    }
}
