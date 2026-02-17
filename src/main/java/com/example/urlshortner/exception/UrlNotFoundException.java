package com.example.urlshortner.exception;

public class UrlNotFoundException extends RuntimeException {

    public UrlNotFoundException(String shortKey) {
        super("Short URL not found: " + shortKey);
    }
}

