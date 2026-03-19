package br.com.inspectflow.application.bucket.ports.out;

import java.io.InputStream;

public interface GetFileType {
    String execute(InputStream fileUrl);

}
