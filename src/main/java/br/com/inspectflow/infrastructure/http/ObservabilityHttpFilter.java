package br.com.inspectflow.infrastructure.http;

import io.micrometer.observation.ObservationRegistry;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ObservabilityHttpFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    private static final String TRACE_ID_HEADER       = "X-Trace-Id";

    private static final String MDC_CORRELATION_ID = "correlationId";
    private static final String MDC_USER_ID        = "userId";
    private static final String MDC_HTTP_METHOD    = "httpMethod";
    private static final String MDC_REQUEST_URI    = "requestUri";
    private static final String MDC_CLIENT_IP      = "clientIp";

    private final ObservationRegistry observationRegistry;

    public ObservabilityHttpFilter(ObservationRegistry observationRegistry) {
        this.observationRegistry = observationRegistry;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            enrichMdc(request);
            addCorrelationIdToResponse(request, response);

            filterChain.doFilter(request, response);

            addTraceIdToResponse(response);

        } finally {
            MDC.clear();
        }
    }

    private void enrichMdc(HttpServletRequest request) {

        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }
        MDC.put(MDC_CORRELATION_ID, correlationId);

        MDC.put(MDC_HTTP_METHOD, request.getMethod());
        MDC.put(MDC_REQUEST_URI, request.getRequestURI());

        String clientIp = getClientIp(request);
        MDC.put(MDC_CLIENT_IP, clientIp);

    }

    private void addCorrelationIdToResponse(HttpServletRequest request,
                                            HttpServletResponse response) {
        String correlationId = MDC.get(MDC_CORRELATION_ID);
        if (correlationId != null) {
            response.setHeader(CORRELATION_ID_HEADER, correlationId);
        }
    }

    private void addTraceIdToResponse(HttpServletResponse response) {
        String traceId = MDC.get("traceId");
        if (traceId != null && !traceId.isBlank()) {
            response.setHeader(TRACE_ID_HEADER, traceId);
        }
    }

    /**
     * Extrai o IP do cliente considerando proxies reversos.
     * Ordem de prioridade: X-Forwarded-For → X-Real-IP → RemoteAddr
     */
    private String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            return xff.split(",")[0].trim();
        }
        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isBlank()) {
            return realIp;
        }
        return request.getRemoteAddr();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.startsWith("/actuator");
    }
}