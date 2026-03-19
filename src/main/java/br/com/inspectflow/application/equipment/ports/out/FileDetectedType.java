package br.com.inspectflow.application.equipment.ports.out;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileDetectedType {
    boolean execute(MultipartFile fileInputStream, List<String> acceptedType);
}
