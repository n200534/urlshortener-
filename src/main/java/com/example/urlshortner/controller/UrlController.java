package com.example.urlshortner.controller;

import com.example.urlshortner.dto.ApiResponse;
import com.example.urlshortner.dto.ShortenUrlRequest;
import com.example.urlshortner.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/api/shorten")
    public ResponseEntity<ApiResponse<String>> shortenUrl(
            @Valid @RequestBody ShortenUrlRequest request) {

        String shortKey = urlService.shortenUrl(request);

        String shortUrl = "http://localhost:8080/u/" + shortKey;

        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .message("URL shortened successfully")
                .data(shortUrl)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/u/{shortKey}")
    public void redirect(
            @PathVariable String shortKey,
            HttpServletResponse response) throws IOException {

        String originalUrl = urlService.getOriginalUrl(shortKey);

        response.sendRedirect(originalUrl);
    }

    @GetMapping("/api/analytics/{shortKey}")
    public ResponseEntity<?> getAnalytics(@PathVariable String shortKey) {

        return ResponseEntity.ok(urlService.getAnalytics(shortKey));
    }

}
