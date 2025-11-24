package com.codlibs.smartnews.context;

import com.codlibs.smartnews.cache.UserContextCache;
import com.codlibs.smartnews.cache.UserDemographics;
import com.launchdarkly.sdk.LDContext;
import org.springframework.stereotype.Service;

@Service
public class UserContextService {
    private final UserContextCache userContextCache;
    private final LaunchDarklyContextFactory contextFactory;

    public UserContextService(UserContextCache userContextCache, LaunchDarklyContextFactory contextFactory) {
        this.userContextCache = userContextCache;
        this.contextFactory = contextFactory;
    }

    public LDContext buildContextFor(String userId,
                                     String deviceType,
                                     String appVersion) {
        UserDemographics userDemographics = userContextCache.getUserDemographics(userId);
        return contextFactory.buildContext(userDemographics, deviceType, appVersion);
    }
}
