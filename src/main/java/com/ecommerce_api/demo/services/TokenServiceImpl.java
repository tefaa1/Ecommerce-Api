package com.ecommerce_api.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TokenServiceImpl implements TokenService {

    private static final String PREFIX_REFRESH = "refresh:";
    private static final String PREFIX_ACCESS = "access:";
    private static final String PREFIX_REFRESH_BLACKLIST = "blacklist:refresh:";
    private static final String PREFIX_ACCESS_BLACKLIST = "blacklist:access:";

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public TokenServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveRefreshToken(String email, String token, long millis) {
        redisTemplate.opsForValue().set(PREFIX_REFRESH + email, token, Duration.ofMillis(millis));
    }

    @Override
    public void saveAccessToken(String email, String token, long millis) {
        redisTemplate.opsForValue().set(PREFIX_ACCESS + email, token, Duration.ofMillis(millis));
    }

    @Override
    public String getRefreshToken(String email) {
        return redisTemplate.opsForValue().get(PREFIX_REFRESH + email);
    }

    @Override
    public String getAccessToken(String email) {
        return redisTemplate.opsForValue().get(PREFIX_ACCESS + email);
    }

    @Override
    public void deleteRefreshToken(String email) {
        redisTemplate.delete(PREFIX_REFRESH + email);
    }

    @Override
    public void deleteAccessToken(String email) {
        redisTemplate.delete(PREFIX_ACCESS + email);
    }

    @Override
    public boolean isRefreshTokenValid(String email, String incomingToken) {
        String stored = getRefreshToken(email);
        return stored != null && stored.equals(incomingToken);
    }

    @Override
    public boolean isAccessTokenValid(String email, String incomingToken) {
        String stored = getAccessToken(email);
        return stored != null && stored.equals(incomingToken);
    }

    @Override
    public void blacklistAccessToken(String token, long millis) {
        redisTemplate.opsForValue().set(PREFIX_ACCESS_BLACKLIST + token, "true", Duration.ofMillis(millis));
    }

    @Override
    public void blacklistRefreshToken(String token, long millis) {
        redisTemplate.opsForValue().set(PREFIX_REFRESH_BLACKLIST + token, "true", Duration.ofMillis(millis));
    }

    @Override
    public boolean isBlacklisted(String token) {
        return redisTemplate.hasKey(PREFIX_ACCESS_BLACKLIST + token) || redisTemplate.hasKey(PREFIX_REFRESH_BLACKLIST + token);
    }
}
