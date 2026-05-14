package br.com.inspectflow.infrastructure.config.observability;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SchedulingObservabilityConfig {

    private final ObservationRegistry observationRegistry;

    public SchedulingObservabilityConfig(ObservationRegistry observationRegistry) {
        this.observationRegistry = observationRegistry;
    }

    /**
     * Intercepta qualquer método anotado com @Scheduled.
     * Cria um Span + métricas para cada execução do job.
     */
    @Around("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public Object observeScheduledJob(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String jobName    = signature.getMethod().getName();
        String className  = joinPoint.getTarget().getClass().getSimpleName();
        String observationName = "scheduled.job";

        // Cria a observação (Span + métricas)
        Observation observation = Observation.createNotStarted(observationName, observationRegistry)
                .contextualName(className + "." + jobName)
                .lowCardinalityKeyValue("job.name",  jobName)
                .lowCardinalityKeyValue("job.class", className);

        return observation.observeChecked(() -> {
            try {
                Object result = joinPoint.proceed();
                observation.lowCardinalityKeyValue("job.status", "success");
                return result;
            } catch (Throwable ex) {
                observation.lowCardinalityKeyValue("job.status", "error");
                observation.error(ex);
                throw ex;
            }
        });
    }
}