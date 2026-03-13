package br.com.inspectflow.infrastructure.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CookieBearerTokenResolver tokenResolver;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(request -> {
                    request.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/v3/api-docs").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/scalar/**").permitAll();
                    request.anyRequest().authenticated();
                        }
                )

                .oauth2ResourceServer(oauth ->
                        oauth
                                .bearerTokenResolver(tokenResolver)
                                .jwt(Customizer.withDefaults())
                );

        return http.build();
    }
}