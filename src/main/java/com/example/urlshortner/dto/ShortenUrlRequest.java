package com.example.urlshortner.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortenUrlRequest {

    @NotBlank(message = "URL cannot be empty")
    private String longUrl;
    private String customAlias;   // optional
    private Long expiryInMinutes;
}
