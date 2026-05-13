package com.example.urlshortner.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyUrlResponse {

    private String shortKey;
    private String longUrl;
    private Long clickCount;
}