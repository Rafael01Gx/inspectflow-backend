package br.com.inspectflow.infrastructure.config.security;

import br.com.inspectflow.infrastructure.config.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.converter.RsaKeyConverters;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@RequiredArgsConstructor
public class RsaKeyConfig {

    private final JwtProperties jwtProperties;
    private final ResourceLoader resourceLoader;

    @Bean
    public RSAPublicKey publicKey() throws Exception {

        var resource = resourceLoader.getResource(jwtProperties.publicKeyPath());

        return (RSAPublicKey) RsaKeyConverters
                .x509()
                .convert(resource.getInputStream());
    }

    @Bean
    public RSAPrivateKey privateKey() throws Exception {

        var resource = resourceLoader.getResource(jwtProperties.privateKeyPath());

        return (RSAPrivateKey) RsaKeyConverters
                .pkcs8()
                .convert(resource.getInputStream());
    }
}