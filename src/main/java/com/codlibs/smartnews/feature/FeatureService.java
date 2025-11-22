package com.codlibs.smartnews.feature;


import com.codlibs.smartnews.context.LaunchDarklyContextFactory;
import com.launchdarkly.sdk.server.LDClient;
import org.springframework.stereotype.Service;

@Service
public class FeatureService {

    public static final String ENABLE_NEW_HOME_LAYOUT_LD_KEY = "enable-new-home-layout";
    public static final boolean DEFAULT_VALUE = false;
    private final LDClient ldClient;

    private final LaunchDarklyContextFactory contextFactory;

    public FeatureService(LDClient ldClient, LaunchDarklyContextFactory contextFactory) {
        this.ldClient = ldClient;
        this.contextFactory = contextFactory;
    }

    public boolean isNewHomeEnabled(String userId) {
        var context = contextFactory.buildBasicUserContext(userId);
        return ldClient.boolVariation(
                ENABLE_NEW_HOME_LAYOUT_LD_KEY,
                context,
                DEFAULT_VALUE
        );
    }
}
