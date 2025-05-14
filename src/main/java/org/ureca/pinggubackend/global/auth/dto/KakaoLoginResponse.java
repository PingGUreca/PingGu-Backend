package org.ureca.pinggubackend.global.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoLoginResponse {

    // 우리가 발급한 자체 JWT토큰들
    private final String accessToken;
    private final String refreshToken;

    // 기존 회원인지 여부
    // false면 회원가입을 해야하므로 위의 토큰들은 안보냄
    // true면 바로 로그인을 시켜주면 되므로 토큰들을 보냄
    private final boolean isRegister;

    private final Long userId;
}
