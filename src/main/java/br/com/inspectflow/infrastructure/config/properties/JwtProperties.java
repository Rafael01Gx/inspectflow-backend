package br.com.inspectflow.infrastructure.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security.jwt")
public record JwtProperties(
        Long expiration,
        String cookieName,
        Boolean cookieSecure,
        Boolean cookieHttpOnly,
        String cookieSameSite,
        String privateKeyPath,
        String publicKeyPath
) {
}
