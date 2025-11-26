package com.codlibs.smartnews.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("launchdarkly.feature-flags")
public class LaunchDarklyFeatureFlag {
    private final FeatureFlagConfig<Boolean> enableNewHome;
    private final FeatureFlagConfig<Boolean> enableOffers;
    private final FeatureFlagConfig<Boolean> enableLocalNews;
    private final FeatureFlagConfig<String> recommendationAlgoVersion;
    private final FeatureFlagConfig<String> promoBannerType;

    public LaunchDarklyFeatureFlag(FeatureFlagConfig<Boolean> enableNewHome, FeatureFlagConfig<Boolean> enableOffers, FeatureFlagConfig<Boolean> enableLocalNews,
                                   FeatureFlagConfig<String> recommendationAlgoVersion, FeatureFlagConfig<String> promoBannerType) {
        this.enableNewHome = enableNewHome;
        this.enableOffers = enableOffers;
        this.enableLocalNews = enableLocalNews;
        this.recommendationAlgoVersion = recommendationAlgoVersion;
        this.promoBannerType = promoBannerType;
    }

    public FeatureFlagConfig<Boolean> getEnableNewHome() {
        return enableNewHome;
    }

    public FeatureFlagConfig<Boolean> getEnableOffers() {
        return enableOffers;
    }

    public FeatureFlagConfig<Boolean> getEnableLocalNews() {
        return enableLocalNews;
    }

    public FeatureFlagConfig<String> getRecommendationAlgoVersion() {
        return recommendationAlgoVersion;
    }

    public FeatureFlagConfig<String> getPromoBannerType() {
        return promoBannerType;
    }

    public static class FeatureFlagConfig<T> {
        private final String name;
        private final T offlineDefault;

        public FeatureFlagConfig(String name, T offlineDefault) {
            this.name = name;
            this.offlineDefault = offlineDefault;
        }

        public String getName() {
            return name;
        }

        public T getOfflineDefault() {
            return offlineDefault;
        }
    }
}
