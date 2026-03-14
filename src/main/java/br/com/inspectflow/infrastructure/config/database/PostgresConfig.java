package br.com.inspectflow.infrastructure.config.database;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "br.com.inspectflow.infrastructure.persistence.postgres.repositories"
)
public class PostgresConfig {
}
