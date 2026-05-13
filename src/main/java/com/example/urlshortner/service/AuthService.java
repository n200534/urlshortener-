package com.example.urlshortner.service;

import com.example.urlshortener.dto.RegisterRequest;
import com.example.urlshortner.dto.LoginRequest;

public interface AuthService {

    void register(RegisterRequest request);
    String login(LoginRequest request);
}