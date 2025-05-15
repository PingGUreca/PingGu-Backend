package org.ureca.pinggubackend.domain.auth.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ureca.pinggubackend.domain.auth.dto.*;
import org.ureca.pinggubackend.domain.auth.service.AuthService;

import java.io.IOException;
import java.time.Duration;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService kakaoOAuthService;
    private final AuthService authService;
    
    @GetMapping("/kakao-login")
    public ResponseEntity<KakaoLoginResponse> kakaoLogin(@RequestParam("code") String code) throws IOException {
        return ResponseEntity.ok(kakaoOAuthService.loginOrRegister(code));
    }

    @GetMapping("/token")
    public ResponseEntity<JwtReissueResponse> reissueAccessToken(@CookieValue("refresh_token") String refreshToken) {
        return ResponseEntity.ok(authService.reissueAccessToken(refreshToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        SignupResponse response = authService.signup(request);
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", response.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .maxAge(Duration.ofDays(14))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getAccessToken())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(response);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(@CookieValue("refresh_token") String refreshToken) {
        LogoutResponse response = authService.logout(refreshToken);

        ResponseCookie deleteCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body(response);
    }
}