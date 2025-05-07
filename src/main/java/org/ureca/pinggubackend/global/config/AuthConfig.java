package org.ureca.pinggubackend.global.config;

import org.springframework.stereotype.Component;

@Component

public class AuthConfig {
    private String clientId;
    private String redirectUri;
    private String clientSecret;

    public String getClientSecret() {
        return clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

}
