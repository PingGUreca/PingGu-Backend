package org.ureca.pinggubackend.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoLoginResponse {
    private final Long userId;
    private final String accessToken;
    private final String refreshToken;
    private final boolean isRegister;
}
