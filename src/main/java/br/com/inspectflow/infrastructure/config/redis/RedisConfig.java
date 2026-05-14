package br.com.inspectflow.infrastructure.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        RedisSerializer<Object> jsonSerializer = RedisSerializer.json();

        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .disableCachingNullValues()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string())
                )
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer)
                );

        Map<String, RedisCacheConfiguration> cacheConfigurations = Map.ofEntries(
                // ── Dashboard Analytics
                Map.entry("dashboardKpis",              defaultConfig.entryTtl(Duration.ofMinutes(30))),
                Map.entry("dashboardEquipments",              defaultConfig.entryTtl(Duration.ofMinutes(30))),
                Map.entry("dashboardInspections",              defaultConfig.entryTtl(Duration.ofMinutes(30))),
                Map.entry("dashboardStockItems",              defaultConfig.entryTtl(Duration.ofMinutes(30))),
                Map.entry("dashboardWorkOrderMonthlyStatusCounts",              defaultConfig.entryTtl(Duration.ofMinutes(30))),
                Map.entry("dashboardWorkOrderStatusCounts",              defaultConfig.entryTtl(Duration.ofMinutes(30))),


                // ── PlantHealth Analytics
                Map.entry("plantHealthCriticalStock",              defaultConfig.entryTtl(Duration.ofMinutes(30))),
                Map.entry("plantHealthOpenOrders",              defaultConfig.entryTtl(Duration.ofMinutes(30))),
                Map.entry("plantHealthOverview",              defaultConfig.entryTtl(Duration.ofMinutes(30))),
                Map.entry("plantHealthOverdue",              defaultConfig.entryTtl(Duration.ofMinutes(30))),


                // ── Equipment Analytics
//                Map.entry("equipmentTopByOrders",       defaultConfig.entryTtl(Duration.ofMinutes(10))),
//                Map.entry("equipmentTopParts",          defaultConfig.entryTtl(Duration.ofMinutes(10))),
//                Map.entry("equipmentFailureTrend",      defaultConfig.entryTtl(Duration.ofMinutes(10))),
//                Map.entry("equipmentResolutionRanking", defaultConfig.entryTtl(Duration.ofMinutes(10))),

                // ── Personal Dashboard
                Map.entry("dashboardPersonalFull",            defaultConfig.entryTtl(Duration.ofMinutes(10)))
        );

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }
}
