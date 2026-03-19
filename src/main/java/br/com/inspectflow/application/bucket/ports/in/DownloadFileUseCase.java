package br.com.inspectflow.application.bucket.ports.in;

import br.com.inspectflow.domain.bucket.dto.DownloadResponse;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.UUID;


public interface DownloadFileUseCase {
    DownloadResponse execute(UUID id);
}
