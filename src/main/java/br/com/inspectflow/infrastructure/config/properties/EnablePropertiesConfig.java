package br.com.inspectflow.infrastructure.config.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        JwtProperties.class,
        MongoDBProperties.class,
        MinioProperties.class,
        AppHostProperties.class,
        SpringApplicationProperties.class
})
public class EnablePropertiesConfig {
}
