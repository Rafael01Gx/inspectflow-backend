package br.com.inspectflow.infrastructure.persistence.bucket.repositories;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileUseCase {
    void execute(String fileNameLocalNameUrl, MultipartFile file);

}
