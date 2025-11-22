package com.codlibs.smartnews.config;

import com.launchdarkly.sdk.server.LDClient;
import com.launchdarkly.sdk.server.LDConfig;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LaunchDarklyConfig implements DisposableBean {

    private LDClient ldClient;

    @Bean
    public LDClient ldClient(@Value("${launchdarkly.sdk-key}") String sdkKey) {
        LDConfig config = new LDConfig.Builder()
                .build();

        this.ldClient = new LDClient(sdkKey, config);
        return this.ldClient;
    }


    @Override
    public void destroy() throws Exception {
        if (ldClient != null) {
            ldClient.close();
        }
    }
}
