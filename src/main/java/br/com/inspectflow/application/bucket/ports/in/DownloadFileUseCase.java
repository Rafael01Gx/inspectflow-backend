package br.com.inspectflow.application.bucket.ports.in;

import br.com.inspectflow.domain.bucket.dto.DownloadResponse;

import java.util.UUID;


public interface DownloadFileUseCase {
    DownloadResponse execute(UUID id);
}
