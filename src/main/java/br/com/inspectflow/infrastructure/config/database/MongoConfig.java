package br.com.inspectflow.infrastructure.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingEntityCallback;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableMongoRepositories(
        basePackages = "br.com.inspectflow.infrastructure.persistence.mongo.repositories"
)
public class MongoConfig {

    @Bean
    public ValidatingEntityCallback validatingEntityCallback(LocalValidatorFactoryBean factory) {
        return new ValidatingEntityCallback(factory);
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
}
