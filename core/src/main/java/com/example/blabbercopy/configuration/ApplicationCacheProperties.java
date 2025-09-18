package com.example.blabbercopy.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "app.cache")
public class ApplicationCacheProperties {
    private final List<String> cacheNames = new ArrayList<>();
    private final Map<String,CacheProperties> caches = new HashMap();
    @Data
    public static class CacheProperties{
        private Duration expiry = Duration.ZERO;
    }

}
