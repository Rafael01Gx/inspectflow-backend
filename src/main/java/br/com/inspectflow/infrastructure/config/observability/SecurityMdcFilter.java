package br.com.inspectflow.infrastructure.config.observability;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(200)
public class SecurityMdcFilter extends OncePerRequestFilter {

    private static final String MDC_USER_ID    = "userId";
    private static final String MDC_USER_EMAIL = "userEmail";
    private static final String MDC_USER_ROLES = "userRoles";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            populateMdcFromSecurityContext();
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_USER_ID);
            MDC.remove(MDC_USER_EMAIL);
            MDC.remove(MDC_USER_ROLES);
        }
    }

    /**
     * Extrai dados do JWT e popula o MDC.
     *
     * Claims esperadas no JWT (configuráveis no Authorization Server):
     *   sub   → userId (padrão OAuth2)
     *   email → email do usuário
     *   roles → lista de papéis
     */
    private void populateMdcFromSecurityContext() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return;
        }

        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();

            String userId = jwt.getSubject();
            if (userId != null) {
                MDC.put(MDC_USER_ID, userId);
            }

            String email = jwt.getClaimAsString("email");
            if (email != null) {
                MDC.put(MDC_USER_EMAIL, email);
            }

            var authorities = jwtAuth.getAuthorities();
            if (authorities != null && !authorities.isEmpty()) {
                String roles = authorities.stream()
                        .map(Object::toString)
                        .reduce((a, b) -> a + "," + b)
                        .orElse("");
                MDC.put(MDC_USER_ROLES, roles);
            }
            return;
        }

        // Autenticação simples (username/password) — fallback
        String principal = auth.getName();
        if (principal != null) {
            MDC.put(MDC_USER_ID, principal);
        }
    }

    /**
     * Não aplica o filtro para endpoints do Actuator.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.startsWith("/actuator");
    }
}