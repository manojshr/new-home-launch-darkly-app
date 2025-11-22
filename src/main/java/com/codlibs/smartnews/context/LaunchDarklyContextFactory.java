package com.codlibs.smartnews.context;

import com.launchdarkly.sdk.LDContext;
import org.springframework.stereotype.Component;

@Component
public class LaunchDarklyContextFactory {

    public LDContext buildBasicUserContext(String userId) {
        return LDContext.builder("user")
                .key(userId)
                .set("country", "IN")
                .set("deviceType", "mobile")
                .build();
    }
}
