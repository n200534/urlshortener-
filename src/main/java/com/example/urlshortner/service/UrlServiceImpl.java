package com.example.urlshortner.service;

import com.example.urlshortner.CacheService;
import com.example.urlshortner.dto.MyUrlResponse;
import com.example.urlshortner.dto.ShortenUrlRequest;
import com.example.urlshortner.dto.UrlAnalyticsResponse;
import com.example.urlshortner.entity.UrlMapping;
import com.example.urlshortner.entity.User;
import com.example.urlshortner.exception.UrlNotFoundException;
import com.example.urlshortner.repository.UrlMappingRepository;
import com.example.urlshortner.repository.UserRepository;
import com.example.urlshortner.security.SecurityUtil;
import com.example.urlshortner.util.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlMappingRepository repository;
    private final UserRepository userRepository;
    private final CacheService cacheService;

    @Override
    public String shortenUrl(ShortenUrlRequest request) {

        String shortKey;

        // Get currently authenticated user
        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Custom alias logic
        if (request.getCustomAlias() != null &&
                !request.getCustomAlias().isBlank()) {

            if (repository.existsByShortKey(request.getCustomAlias())) {
                throw new RuntimeException("Custom alias already exists");
            }

            shortKey = request.getCustomAlias();

        } else {

            Long id = repository.getNextSequenceValue();
            shortKey = Base62Encoder.encode(id);
        }

        // Expiry logic
        LocalDateTime expiresAt = null;

        if (request.getExpiryInMinutes() != null) {
            expiresAt = LocalDateTime.now()
                    .plusMinutes(request.getExpiryInMinutes());
        }

        // Create URL mapping
        UrlMapping mapping = UrlMapping.builder()
                .longUrl(request.getLongUrl())
                .shortKey(shortKey)
                .createdAt(LocalDateTime.now())
                .expiresAt(expiresAt)
                .clickCount(0L)
                .user(user)
                .build();

        repository.save(mapping);

        return shortKey;
    }

    @Override
    public String getOriginalUrl(String shortKey) {

        UrlMapping mapping =
                cacheService.getUrlMapping(shortKey);

        // Expiry validation
        if (mapping.getExpiresAt() != null &&
                mapping.getExpiresAt()
                        .isBefore(LocalDateTime.now())) {

            throw new RuntimeException("URL has expired");
        }

        // Analytics tracking
        mapping.setClickCount(mapping.getClickCount() + 1);

        repository.save(mapping);

        return mapping.getLongUrl();
    }

    @Override
    public UrlAnalyticsResponse getAnalytics(String shortKey) {

        UrlMapping mapping = repository.findByShortKey(shortKey)
                .orElseThrow(() ->
                        new UrlNotFoundException(shortKey));

        return UrlAnalyticsResponse.builder()
                .shortKey(mapping.getShortKey())
                .longUrl(mapping.getLongUrl())
                .clickCount(mapping.getClickCount())
                .createdAt(mapping.getCreatedAt())
                .expiresAt(mapping.getExpiresAt())
                .build();
    }

    @Cacheable(value = "urls", key = "#shortKey")
    public UrlMapping getUrlMapping(String shortKey) {

        System.out.println("Fetching from DB...");

        return repository.findByShortKey(shortKey)
                .orElseThrow(() ->
                        new UrlNotFoundException(shortKey));
    }

    @Override
    public List<MyUrlResponse> getMyUrls() {

        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return repository.findByUser(user)
                .stream()
                .map(url -> MyUrlResponse.builder()
                        .shortKey(url.getShortKey())
                        .longUrl(url.getLongUrl())
                        .clickCount(url.getClickCount())
                        .build()
                )
                .toList();
    }
}