package org.ureca.pinggubackend.domain.auth.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ureca.pinggubackend.domain.auth.dto.*;
import org.ureca.pinggubackend.domain.auth.service.AuthService;
import org.ureca.pinggubackend.global.util.AuthResponseUtil;

import java.io.IOException;
import java.time.Duration;

import static org.ureca.pinggubackend.global.util.AuthResponseUtil.buildRefreshCookie;


@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService kakaoOAuthService;
    private final AuthService authService;

    @GetMapping("/kakao-login")
    public ResponseEntity<KakaoRedirectResponse> kakaoLogin(@RequestParam("code") String code) {
        KakaoLoginResponse result = kakaoOAuthService.loginOrRegister(code);

        HttpHeaders headers = new HttpHeaders();

        if (result.getAccessToken() != null && result.getRefreshToken() != null) {
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + result.getAccessToken());
            headers.add(HttpHeaders.SET_COOKIE,
                    AuthResponseUtil.buildRefreshCookie(result.getRefreshToken()).toString());
        }

        KakaoRedirectResponse body = new KakaoRedirectResponse(result.getMemberId(), result.isRegister());

        return ResponseEntity.ok()
                .headers(headers)
                .body(body);
    }


    @GetMapping("/token")
    public ResponseEntity<Void> reissueAccessToken(@CookieValue("refresh_token") String refreshToken) {
        JwtReissueResponse newTokens = authService.reissueAccessToken(refreshToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + newTokens.getAccessToken())
                .header(HttpHeaders.SET_COOKIE, buildRefreshCookie(newTokens.getRefreshToken()).toString())
                .build();
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        SignupResponse response = authService.signup(request);

        return ResponseEntity.ok()
                .headers(AuthResponseUtil.buildAuthHeaders(response.getAccessToken(), response.getRefreshToken()))
                .body(response);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(@CookieValue("refresh_token") String refreshToken) {
        LogoutResponse response = authService.logout(refreshToken);

        return ResponseEntity.ok()
                .header("Set-Cookie", AuthResponseUtil.deleteRefreshCookie().toString())
                .body(response);
    }
}
