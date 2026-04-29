package br.com.inspectflow.infrastructure.persistence.bucket.repositories;

import java.io.InputStream;

public interface GetFileUseCase {

    InputStream execute(String fileUrl);
}
