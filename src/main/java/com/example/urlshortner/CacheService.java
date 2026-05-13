package com.example.urlshortner;

import com.example.urlshortner.entity.UrlMapping;
import com.example.urlshortner.exception.UrlNotFoundException;
import com.example.urlshortner.repository.UrlMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final UrlMappingRepository repository;

    @Cacheable(value = "urls", key = "#shortKey")
    public UrlMapping getUrlMapping(String shortKey) {

        System.out.println("Fetching from DB...");

        return repository.findByShortKey(shortKey)
                .orElseThrow(() ->
                        new UrlNotFoundException(shortKey));
    }
}