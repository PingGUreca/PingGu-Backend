package org.ureca.pinggubackend.global.auth.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ureca.pinggubackend.global.auth.dto.KakaoLoginResponse;
import org.ureca.pinggubackend.global.auth.dto.SignupRequest;
import org.ureca.pinggubackend.global.auth.service.AuthService;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService kakaoOAuthService;
    private final AuthService authService;

    // 카카오 로그인
    @GetMapping("/kakao-login")
    public ResponseEntity<KakaoLoginResponse> kakaoLogin(@RequestParam("code") String code) {
       return ResponseEntity.ok(kakaoOAuthService.loginOrRegister(code));
    }

    // 토큰 재발급
    @GetMapping("/token")
    public ResponseEntity<?> reissueAccessToken(@CookieValue("refresh_token") String refreshToken) {
        return authService.reissueAccessToken(refreshToken);
    }

    // 회원가입(설문 조사 이후 회원 정보 저장 api)
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        return authService.signup(request);
    }

    // 로그아웃
    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue("refresh_token") String refreshToken) {
        return authService.logout(refreshToken);
    }

}