package org.ureca.pinggubackend.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupResponse {
    private Long memberId;
    private String accessToken;
    private String refreshToken;
}