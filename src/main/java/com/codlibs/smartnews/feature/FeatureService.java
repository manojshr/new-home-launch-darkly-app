package com.codlibs.smartnews.feature;

import com.codlibs.smartnews.context.UserContextService;
import com.launchdarkly.sdk.server.LDClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FeatureService {

    public static final String ENABLE_NEW_HOME_LAYOUT_LD_KEY = "enable-new-home-layout";
    public static final boolean DEFAULT_VALUE = false;
    private final LDClient ldClient;

    @Value("${launchdarkly.feature-flags.enable-new-home.name}")
    private String enableNewHomeLayoutKey;

    @Value("${launchdarkly.feature-flags.enable-new-home.default}")
    private boolean enableNewHomeLayoutDefault;

    private final UserContextService userContextService;

    public FeatureService(LDClient ldClient, UserContextService userContextService) {
        this.userContextService = userContextService;
        this.ldClient = ldClient;
    }

    public boolean isNewHomeEnabled(String userId,
                                    String deviceType,
                                    String appVersion) {
        var context = userContextService.buildContextFor(userId, deviceType, appVersion);
        return ldClient.boolVariation(
                enableNewHomeLayoutKey,
                context,
                enableNewHomeLayoutDefault
        );
    }
}
