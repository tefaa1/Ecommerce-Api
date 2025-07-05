package com.ecommerce_api.demo.services;

import java.time.Duration;

public interface TokenService {
    public void saveRefreshToken(String email, String token, long millis);
    public void saveAccessToken(String email, String token, long millis);
    public String getRefreshToken(String email);
    public String getAccessToken(String email);
    public void deleteRefreshToken(String email);
    public void deleteAccessToken(String email);
    public boolean isRefreshTokenValid(String email, String incomingToken);
    public boolean isAccessTokenValid(String email, String incomingToken);
    public void blacklistAccessToken(String token, long millis);
    public void blacklistRefreshToken(String token, long millis);
    public boolean isBlacklisted(String token);
}
