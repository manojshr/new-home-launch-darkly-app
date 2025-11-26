package com.codlibs.smartnews.dto;

public record FullHomeResponse(String layout, boolean offers, boolean localNews, String recommendationAlgorithm, String banner) {
}
