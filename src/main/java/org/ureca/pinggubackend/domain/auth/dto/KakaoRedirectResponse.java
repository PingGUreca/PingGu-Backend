package org.ureca.pinggubackend.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoRedirectResponse {
    private Long memberId;
    private boolean isRegistered;
}
