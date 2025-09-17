package org.example.jws_gruppuppgift.configs;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CacheConfig
{
    @Bean
    public Caffeine<Object, Object> caffeineConfig()
    {
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(30))
                .maximumSize(500);
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine)
    {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("exchangeRate");
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}
