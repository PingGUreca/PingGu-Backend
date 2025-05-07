package org.ureca.pinggubackend.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ureca.pinggubackend.global.dto.KakaoProfile;
import org.ureca.pinggubackend.global.service.KakaoOAuthService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final KakaoOAuthService kakaoOAuthService;
//    private final Jwtutil jwtUtil;

    public AuthController(KakaoOAuthService kakaoOAuthService) {
        this.kakaoOAuthService = kakaoOAuthService;
//        this.jwtUtil = jwtUtil;
    }

    //    @PostMapping("/kakao-login")
    @GetMapping("/kakao-login")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code) {
        KakaoProfile profile = kakaoOAuthService.getUserProfile(code);
//        String email = profile.getKakao_account().getEmail();
//
//        // jwt 생성
//        String token = jwtUtil.generateToken(email);
//
//        // 응답 JSON 구성
//        Map<String, String> response = new HashMap<>();
//        response.put("token", token);
//        response.put("email", email);


        return ResponseEntity.ok(profile);
    }
}
