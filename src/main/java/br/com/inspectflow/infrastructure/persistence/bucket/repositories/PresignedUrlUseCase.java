package br.com.inspectflow.infrastructure.persistence.bucket.repositories;

public interface PresignedUrlUseCase {
    String execute(String objectKey);

}
