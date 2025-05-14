package org.ureca.pinggubackend.global.auth.dto;

public record LoginResponse(String accessToken, String refreshToken, Long memberId) {

    public static LoginResponse fromToken(String accessToken, String refreshToken) {
        return new LoginResponse(accessToken, refreshToken, null);
    }

    public static LoginResponse fromNewMember(Long memberId) {
        return new LoginResponse(null, null, memberId);
    }
}