package org.nastya.controller;

import org.nastya.dto.SessionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserAuthorizationChecker {
    private static final Logger log = LoggerFactory.getLogger(UserAuthorizationChecker.class);
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public UserAuthorizationChecker(RestTemplate restTemplate, @Value("${book-shop.authenticator}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public boolean isUserAuthorized(String sessionId) {
        log.info("Checking authorization for sessionId: {}", sessionId);
        String url = baseUrl + "/api/session/status/" + sessionId;
        log.info("Base URL: {}", baseUrl);
        ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.GET, null, Boolean.class);
        return Boolean.TRUE.equals(response.getBody());
    }

    public Integer getUserIdBySessionId(String sessionId) {
        log.info("Fetching user ID for sessionId: {}", sessionId);
        String url = baseUrl + "/api/session/" + sessionId;
        ResponseEntity<SessionDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, SessionDTO.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            SessionDTO sessionDTO = response.getBody();
            log.info("User ID found: {}", sessionDTO.getUserId());
            return sessionDTO.getUserId();
        } else {
            log.warn("Session not found or error occurred for sessionId: {}", sessionId);
            return null;
        }
    }
}
