package com.codlibs.smartnews.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class UserContextCache {
    public static final String UNKNOWN = "Unknown";
    private final Cache<String, UserDemographics> cache;
    private final UserDemographicsProvider provider;

    public UserContextCache(UserDemographicsProvider provider) {
        this.provider = provider;
        this.cache = Caffeine.newBuilder()
                .maximumSize(1_000)
                .expireAfterWrite(Duration.ofMinutes(10))
                .build();
    }

    public UserDemographics getUserDemographics(String userId) {
        return cache.get(userId, id -> provider.loadByUserId(id)
                .orElse(new UserDemographics(userId, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, 999)));
    }
}
