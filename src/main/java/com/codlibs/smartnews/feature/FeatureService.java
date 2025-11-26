package com.codlibs.smartnews.feature;

import com.codlibs.smartnews.config.LaunchDarklyFeatureFlag;
import com.codlibs.smartnews.config.LaunchDarklyFeatureFlag.FeatureFlagConfig;
import com.codlibs.smartnews.context.UserContextService;
import com.launchdarkly.sdk.LDContext;
import com.launchdarkly.sdk.server.LDClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(LaunchDarklyFeatureFlag.class)
public class FeatureService {

    public static final String ENABLE_NEW_HOME_LAYOUT_LD_KEY = "enable-new-home-layout";
    public static final boolean DEFAULT_VALUE = false;
    private final LDClient ldClient;
    private final LaunchDarklyFeatureFlag launchDarklyFeatureFlag;

    private final UserContextService userContextService;

    public FeatureService(LDClient ldClient, LaunchDarklyFeatureFlag launchDarklyFeatureFlag, UserContextService userContextService) {
        this.launchDarklyFeatureFlag = launchDarklyFeatureFlag;
        this.userContextService = userContextService;
        this.ldClient = ldClient;
    }

    public boolean isNewHomeEnabled(String userId,
                                    String deviceType,
                                    String appVersion) {
        var context = userContextService.buildContextFor(userId, deviceType, appVersion);
        return getForBoolVariation(launchDarklyFeatureFlag.getEnableNewHome(), context);
    }

    public boolean isOffersEnabled(String userId,
                                    String deviceType,
                                    String appVersion) {
        var context = userContextService.buildContextFor(userId, deviceType, appVersion);
        return getForBoolVariation(launchDarklyFeatureFlag.getEnableOffers(), context);
    }

    public boolean isLocalNewsEnabled(String userId,
                                      String deviceType,
                                      String appVersion) {
        var context = userContextService.buildContextFor(userId, deviceType, appVersion);
        return getForBoolVariation(launchDarklyFeatureFlag.getEnableLocalNews(), context);
    }

    public String getRecommendationAlgoVersion(String userId,
                                                String deviceType,
                                                String appVersion) {
        var context = userContextService.buildContextFor(userId, deviceType, appVersion);
        return getForStringValue(launchDarklyFeatureFlag.getRecommendationAlgoVersion(), context);
    }

    public String getPromoBanner(String userId,
                                 String deviceType,
                                 String appVersion) {
        var context = userContextService.buildContextFor(userId, deviceType, appVersion);
        return getForStringValue(launchDarklyFeatureFlag.getPromoBannerType(), context);
    }

    private boolean getForBoolVariation(FeatureFlagConfig<Boolean> featureFlagConfig, LDContext context) {
        return ldClient.boolVariation(
                featureFlagConfig.getName(),
                context,
                featureFlagConfig.getOfflineDefault()
        );
    }

    private String getForStringValue(FeatureFlagConfig<String> featureFlagConfig, LDContext context) {
        return ldClient.stringVariation(
                featureFlagConfig.getName(),
                context,
                featureFlagConfig.getOfflineDefault()
        );
    }
}
