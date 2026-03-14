package br.com.inspectflow.infrastructure.config.database;

import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "br.com.inspectflow.domain",
includeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JpaRepository.class)
)
public class PostgresConfig {
}
