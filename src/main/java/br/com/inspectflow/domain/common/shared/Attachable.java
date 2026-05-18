package br.com.inspectflow.domain.common.shared;

import java.util.UUID;

public interface Attachable<T extends Enum<T>> {
    UUID getId();

    T getType();
    String getFileName();
    String getFileUrl();
    String getContentType();
}