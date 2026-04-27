package br.com.inspectflow.infrastructure.config.security;

import br.com.inspectflow.domain.user.enums.Role;
import br.com.inspectflow.infrastructure.config.properties.AppHostProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final CookieBearerTokenResolver tokenResolver;
    private final AppHostProperties appHosts;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(request -> {
                    request.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                    request.requestMatchers(
                            HttpMethod.GET,
                            "/equipments/**",
                            "/inspections/equipment/**",
                            "/orders/search/equipment/**",
                            "/attachments/**")
                            .permitAll();
                    request.requestMatchers(HttpMethod.GET, "/attachments/**").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/v3/api-docs").permitAll();
                    request.requestMatchers(HttpMethod.GET, "/scalar/**").permitAll();
                    request.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    request.anyRequest().authenticated();
                        }
                )

                .oauth2ResourceServer(oauth ->
                        oauth
                                .bearerTokenResolver(tokenResolver)
                                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

        grantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4000",appHosts.web(), "https://web.rflgx.com.br"));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST","PATCH","PUT", "DELETE", "OPTIONS"));

        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


    @Bean
    static RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()

                .role(Role.ADMINISTRADOR.name()).implies(Role.GESTOR.name())

                .role(Role.GESTOR.name()).implies(Role.LIDER.name())
                .role(Role.GESTOR.name()).implies(Role.SUPERVISOR.name())

                .role(Role.LIDER.name()).implies(Role.ELETRICISTA.name())
                .role(Role.LIDER.name()).implies(Role.MECANICO.name())

                .role(Role.SUPERVISOR.name()).implies(Role.ELETRICISTA.name())
                .role(Role.SUPERVISOR.name()).implies(Role.MECANICO.name())

                .build();
    }

}