package br.com.inspectflow.infrastructure.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring")
public record SpringApplicationProperties(
        Application application,
        Profiles profiles,
        Mail mail

) {
    public record Application(
            String name
    ){}

    public record Profiles(
            String active
    ){}

    public record Mail(
            String host,
            String port,
            String username,
            String password,
            String from
    ){}
}
