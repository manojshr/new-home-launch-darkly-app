package com.codlibs.smartnews.context;

import com.codlibs.smartnews.cache.UserDemographics;
import com.launchdarkly.sdk.LDContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LaunchDarklyContextFactory {

    public LDContext buildContext(UserDemographics demographics,
                                  String deviceType,
                                  String appVersion) {
        LDContext userContext = LDContext.builder(demographics.userId())
                .kind("user")
                .set("segment", demographics.segment())
                .set("plan", demographics.plan())
                .set("age", demographics.age())
                .build();

        LDContext locationContext = LDContext.builder("loc-" + demographics.country() + "-" + demographics.region())
                .kind("location")
                .set("country", demographics.country())
                .set("region", demographics.region())
                .set("city", demographics.city())
                .build();

        String normalizedDevice = Objects.requireNonNullElse(deviceType, "unknown").toLowerCase();
        String normalizedAppVersion = Objects.requireNonNullElse(appVersion, "unknown");
        LDContext deviceContext = LDContext.builder("device-" + normalizedDevice)
                .kind("device")
                .set("deviceType", normalizedDevice)
                .set("appVersion", normalizedAppVersion)
                .build();

        return LDContext.multiBuilder()
                .add(userContext)
                .add(locationContext)
                .add(deviceContext)
                .build();
    }
}
