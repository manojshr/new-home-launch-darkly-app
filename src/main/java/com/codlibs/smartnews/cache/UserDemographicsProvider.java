package com.codlibs.smartnews.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class UserDemographicsProvider {

    private static final Map<String, UserDemographics> DEMOGRAPHICS_MAP = Map.of(
            "101", new UserDemographics("101", "IN", "MH", "Mumbai", "premium", "GOLD", 25),
            "102", new UserDemographics("102", "IN", "DL", "Delhi", "free", "FREE", 18),
            "201", new UserDemographics("201", "US", "CA", "San Francisco", "premium", "PLATINUM", 35),
            "202", new UserDemographics("202", "US", "NY", "New York", "free", "FREE", 25),
            "203", new UserDemographics("203", "US", "NY", "New York", "free", "FREE", 34)
    );

    public Optional<UserDemographics> loadByUserId(String userId) {
        return Optional.ofNullable(DEMOGRAPHICS_MAP.get(userId));
    }
}
