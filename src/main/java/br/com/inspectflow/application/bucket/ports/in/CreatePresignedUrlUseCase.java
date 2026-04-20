package br.com.inspectflow.application.bucket.ports.in;

public interface CreatePresignedUrlUseCase {

    String execute(String objectKey);
}
