package com.example.urlshortner.controller;

import com.example.urlshortner.dto.ApiResponse;
import com.example.urlshortner.dto.ShortenUrlRequest;
import com.example.urlshortner.ratelimit.RateLimitService;
import com.example.urlshortner.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name="URl APIs")
@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;
    private final RateLimitService rateLimitService;
    @Value("${app.base-url}")
    private String baseUrl;

    @Operation(summary = "Shortens the Long URL into Shorter Onnes")
    @PostMapping("/api/shorten")
    public ResponseEntity<ApiResponse<String>> shortenUrl(
            @RequestBody ShortenUrlRequest request,
            HttpServletRequest servletRequest) {

        String ip = servletRequest.getRemoteAddr();

        // Rate limiting check
        if (!rateLimitService.isAllowed(ip)) {
            throw new RuntimeException("Rate limit exceeded. Try again later.");
        }

        String shortKey = urlService.shortenUrl(request);

        String shortUrl = baseUrl + "/u/" + shortKey;

        ApiResponse<String> response =
                ApiResponse.<String>builder()
                        .success(true)
                        .message("URL shortened successfully")
                        .data(shortUrl)
                        .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Redirects to the Original URL ")
    @GetMapping("/u/{shortKey}")
    public void redirect(
            @PathVariable String shortKey,
            HttpServletResponse response) throws IOException {

        String originalUrl = urlService.getOriginalUrl(shortKey);

        response.sendRedirect(originalUrl);
    }


    @Operation(summary = "Analytics of the Short URL ")
    @GetMapping("/api/analytics/{shortKey}")
    public ResponseEntity<?> getAnalytics(@PathVariable String shortKey) {

        return ResponseEntity.ok(urlService.getAnalytics(shortKey));
    }


    @Operation(summary = "Gets Url of Current User")
    @GetMapping("/api/my-urls")
    public ResponseEntity<?> getMyUrls() {

        return ResponseEntity.ok(urlService.getMyUrls());
    }

}
