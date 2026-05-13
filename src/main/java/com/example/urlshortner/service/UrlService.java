package com.example.urlshortner.service;

import com.example.urlshortner.dto.MyUrlResponse;
import com.example.urlshortner.dto.ShortenUrlRequest;
import com.example.urlshortner.dto.UrlAnalyticsResponse;
import com.example.urlshortner.repository.UserRepository;

import java.util.List;

public interface UrlService {



    String shortenUrl(ShortenUrlRequest request);

    String getOriginalUrl(String shortKey);

    UrlAnalyticsResponse getAnalytics(String shortKey);

    List<MyUrlResponse> getMyUrls();

}