package br.com.inspectflow.application.bucket.services;

import br.com.inspectflow.application.bucket.ports.in.CreatePresignedUrlUseCase;
import br.com.inspectflow.domain.bucket.repository.BucketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePresignedUrlService implements CreatePresignedUrlUseCase {
    private final BucketRepository repository;

    @Override
    public String execute(String objectKey) {
        return repository.getPresignedUrl(objectKey);
    }
}
