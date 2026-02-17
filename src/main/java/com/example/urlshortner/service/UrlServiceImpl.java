package com.example.urlshortner.service;

import com.example.urlshortner.dto.ShortenUrlRequest;
import com.example.urlshortner.dto.UrlAnalyticsResponse;
import com.example.urlshortner.entity.UrlMapping;
import com.example.urlshortner.exception.UrlNotFoundException;
import com.example.urlshortner.repository.UrlMappingRepository;
import com.example.urlshortner.util.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlMappingRepository repository;

    @Override
    public String shortenUrl(ShortenUrlRequest request) {

        String shortKey;

        // 1️⃣ Custom alias logic
        if (request.getCustomAlias() != null && !request.getCustomAlias().isBlank()) {

            if (repository.existsByShortKey(request.getCustomAlias())) {
                throw new RuntimeException("Custom alias already exists");
            }

            shortKey = request.getCustomAlias();

        } else {

            Long id = repository.getNextSequenceValue();
            shortKey = Base62Encoder.encode(id);
        }

        LocalDateTime expiresAt = null;

        if (request.getExpiryInMinutes() != null) {
            expiresAt = LocalDateTime.now().plusMinutes(request.getExpiryInMinutes());
        }

        UrlMapping mapping = UrlMapping.builder()
                .longUrl(request.getLongUrl())
                .shortKey(shortKey)
                .createdAt(LocalDateTime.now())
                .expiresAt(expiresAt)
                .clickCount(0L)
                .build();

        repository.save(mapping);

        return shortKey;
    }




    @Override
    public String getOriginalUrl(String shortKey) {

        UrlMapping mapping = repository.findByShortKey(shortKey)
                .orElseThrow(() -> new UrlNotFoundException(shortKey));

        if (mapping.getExpiresAt() != null &&
                mapping.getExpiresAt().isBefore(LocalDateTime.now())) {

            throw new RuntimeException("URL has expired");
        }

        mapping.setClickCount(mapping.getClickCount() + 1);
        repository.save(mapping);

        return mapping.getLongUrl();
    }

    @Override
    public UrlAnalyticsResponse getAnalytics(String shortKey) {

        UrlMapping mapping = repository.findByShortKey(shortKey)
                .orElseThrow(() -> new UrlNotFoundException(shortKey));

        return UrlAnalyticsResponse.builder()
                .shortKey(mapping.getShortKey())
                .longUrl(mapping.getLongUrl())
                .clickCount(mapping.getClickCount())
                .createdAt(mapping.getCreatedAt())
                .expiresAt(mapping.getExpiresAt())
                .build();
    }



}