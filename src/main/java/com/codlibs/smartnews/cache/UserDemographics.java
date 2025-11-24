package com.codlibs.smartnews.cache;

public record UserDemographics(
        String userId,
        String country,
        String region,
        String city,
        String segment,     // e.g. "premium", "free", "trial"
        String plan,        // e.g. "GOLD", "SILVER", "FREE"
        int age     // e.g. "18-24", "25-34"
) {}
