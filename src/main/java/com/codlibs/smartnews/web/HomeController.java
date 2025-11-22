package com.codlibs.smartnews.web;

import com.codlibs.smartnews.dto.HomeResponse;
import com.codlibs.smartnews.feature.FeatureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {
    private final FeatureService featureService;

    public HomeController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @GetMapping("/home")
    public HomeResponse getHome(@RequestParam String userId) {
        boolean isNewHomeEnabled = featureService.isNewHomeEnabled(userId);
        if (isNewHomeEnabled) {
            return new HomeResponse("NEW_HOME", "You are seeing the new home layout.");
        }
        return new HomeResponse("OLD_HOME", "You are seeing the classic home layout.");
    }
}
