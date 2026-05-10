package br.com.inspectflow.infrastructure.config.database;

import br.com.inspectflow.infrastructure.config.properties.MongoDBProperties;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.bson.UuidRepresentation;
import org.springframework.boot.mongodb.autoconfigure.MongoClientSettingsBuilderCustomizer;
import org.springframework.boot.mongodb.health.MongoHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.ValidatingEntityCallback;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(
        basePackages = "br.com.inspectflow.infrastructure.persistence.mongo.repositories"
)
@RequiredArgsConstructor
public class MongoConfig extends AbstractMongoClientConfiguration {

    private final MongoDBProperties properties;

    @Override
    protected String getDatabaseName() {
        return properties.database();
    }

    @Override
    @Primary
    @Bean
    public MongoClient mongoClient() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(properties.uri()))
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();
        return MongoClients.create(settings);
    }

    @Override
    protected boolean autoIndexCreation() {
        return false;
    }

    @Primary
    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, properties.database());
    }

    @Bean
    public MongoHealthIndicator mongoHealthIndicator(MongoClient mongoClient) {
        return new MongoHealthIndicator(mongoClient);
    }

    @Bean
    public ValidatingEntityCallback validatingEntityCallback(LocalValidatorFactoryBean factory) {
        return new ValidatingEntityCallback(factory);
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public MongoClientSettingsBuilderCustomizer uuidCustomizer() {
        return builder -> builder.uuidRepresentation(UuidRepresentation.STANDARD);
    }
}