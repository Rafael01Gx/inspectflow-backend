package br.com.inspectflow.application.bucket.services;

import br.com.inspectflow.application.bucket.ports.in.DeleteFileUseCase;
import br.com.inspectflow.application.http.handlers.BucketFileNotFoundExeption;
import br.com.inspectflow.domain.bucket.repository.BucketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteFileService implements DeleteFileUseCase {

    private final BucketRepository repository;

    @Override
    public void deleteFile(String url) {
        Optional.ofNullable(url).ifPresentOrElse(repository::deleteFile, () -> { throw new BucketFileNotFoundExeption(); });
    }
}
