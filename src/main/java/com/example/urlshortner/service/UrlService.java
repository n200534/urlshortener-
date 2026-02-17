package com.example.urlshortner.service;

import com.example.urlshortner.dto.ShortenUrlRequest;
import com.example.urlshortner.dto.UrlAnalyticsResponse;

public interface UrlService {



    String shortenUrl(ShortenUrlRequest request);

    String getOriginalUrl(String shortKey);

    UrlAnalyticsResponse getAnalytics(String shortKey);

}