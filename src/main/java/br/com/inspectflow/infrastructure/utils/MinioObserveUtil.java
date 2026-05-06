package br.com.inspectflow.infrastructure.utils;

import br.com.inspectflow.application.http.handlers.MinioOperationException;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class MinioObserveUtil {

    private static final String OBSERVATION_PREFIX = "minio";
    private final ObservationRegistry observationRegistry;

    public <T> T observe(String operation, String bucket, String objectKey,
                          Supplier<T> action) {

        Observation observation = Observation
                .createNotStarted(OBSERVATION_PREFIX + "." + operation, observationRegistry)
                .contextualName("minio " + operation)
                .lowCardinalityKeyValue("minio.operation", operation)
                .lowCardinalityKeyValue("minio.bucket",    bucket)
                .highCardinalityKeyValue("minio.object",   objectKey); // apenas no Jaeger

        return observation.observe(() -> {
            try {
                T result = action.get();
                observation.lowCardinalityKeyValue("minio.status", "success");
                return result;
            } catch (MinioOperationException ex) {
                observation.lowCardinalityKeyValue("minio.status", "error");
                observation.error(ex);
                throw ex;
            }
        });
    }
}
