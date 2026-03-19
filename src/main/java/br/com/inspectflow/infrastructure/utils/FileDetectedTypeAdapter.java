package br.com.inspectflow.infrastructure.utils;

import br.com.inspectflow.application.bucket.ports.out.GetFileType;
import br.com.inspectflow.application.equipment.ports.out.FileDetectedType;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class FileDetectedTypeAdapter implements FileDetectedType, GetFileType {

    private final Tika tika = new Tika();

    @Override
    public boolean execute(MultipartFile file, List<String> acceptedType) {
        try {
            String detectedType = tika.detect(file.getInputStream());

            return acceptedType.contains(detectedType);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String execute(InputStream file) {
        try {
            return tika.detect(file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
