package org.ureca.pinggubackend.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtReissueResponse {
    private String accessToken;
    private String refreshToken;
}
