package org.ureca.pinggubackend.global.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import java.time.Duration;

public class AuthResponseUtil {

    // HttpHeaders만 따로 빌드 (Body 있는 응답용)
    public static HttpHeaders buildAuthHeaders(String accessToken, String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.add(HttpHeaders.SET_COOKIE, buildRefreshCookie(refreshToken).toString());
        return headers;
    }

    // Refresh 토큰 쿠키 생성
    public static ResponseCookie buildRefreshCookie(String refreshToken) {
        return ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .maxAge(Duration.ofDays(14))
                .build();
    }

    // Refresh 토큰 삭제용 쿠키
    public static ResponseCookie deleteRefreshCookie() {
        return ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();
    }
}
