package br.com.inspectflow.infrastructure.persistence.bucket.repositories;

public interface DeleteFileUseCase {
    void execute(String fileUrl);
}
