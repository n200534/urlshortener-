package com.example.urlshortner.ratelimit;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private static final int MAX_REQUESTS = 5;

    private static final long WINDOW_SIZE_MS = 60_000;

    private final Map<String, RequestInfo> requestCounts =
            new ConcurrentHashMap<>();

    public boolean isAllowed(String ip) {

        long now = Instant.now().toEpochMilli();

        requestCounts.putIfAbsent(
                ip,
                new RequestInfo(0, now)
        );

        RequestInfo info = requestCounts.get(ip);

        // Reset window
        if (now - info.windowStart > WINDOW_SIZE_MS) {

            info.count = 0;
            info.windowStart = now;
        }

        // Limit exceeded
        if (info.count >= MAX_REQUESTS) {
            return false;
        }

        info.count++;

        return true;
    }

    private static class RequestInfo {

        int count;

        long windowStart;

        RequestInfo(int count, long windowStart) {
            this.count = count;
            this.windowStart = windowStart;
        }
    }
}