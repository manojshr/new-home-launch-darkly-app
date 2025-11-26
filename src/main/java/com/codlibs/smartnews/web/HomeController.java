package com.codlibs.smartnews.web;

import com.codlibs.smartnews.dto.FullHomeResponse;
import com.codlibs.smartnews.dto.HomeResponse;
import com.codlibs.smartnews.feature.FeatureService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HomeController {
    private final FeatureService featureService;

    public HomeController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @GetMapping("/home")
    public HomeResponse getHome(@RequestParam("userId") String userId,
                                @RequestHeader(value = "X-Device-Type", required = false) String deviceType,
                                @RequestHeader(value = "X-App-Version", required = false) String appVersion) {
        boolean isNewHomeEnabled = featureService.isNewHomeEnabled(userId, deviceType, appVersion);
        if (isNewHomeEnabled) {
            return new HomeResponse("NEW_HOME", "You are seeing the new home layout.");
        }
        return new HomeResponse("OLD_HOME", "You are seeing the classic home layout.");
    }

    @GetMapping("/home/everything")
    public FullHomeResponse homeEverything(@RequestParam("userId") String userId,
                                           @RequestHeader(value = "X-Device-Type", required = false) String deviceType,
                                           @RequestHeader(value = "X-App-Version", required = false) String appVersion) {
        boolean newHome = featureService.isNewHomeEnabled(userId, deviceType, appVersion);
        boolean offers = featureService.isOffersEnabled(userId, deviceType, appVersion);
        boolean localNews = featureService.isLocalNewsEnabled(userId, deviceType, appVersion);
        String recommendationAlgoVersion = featureService.getRecommendationAlgoVersion(userId, deviceType, appVersion);
        String promoBanner = featureService.getPromoBanner(userId, deviceType, appVersion);
        return new FullHomeResponse(newHome ? "NEW_HOME" : "OLD_HOME",
                offers,
                localNews,
                recommendationAlgoVersion,
                promoBanner
        );
    }
}
