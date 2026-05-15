package br.com.inspectflow.application.order.ports.in;

import br.com.inspectflow.domain.bucket.dto.DownloadResponse;

import java.util.UUID;

public interface DownloadOrderDocumentUseCase {
    DownloadResponse execute(UUID id);
}
