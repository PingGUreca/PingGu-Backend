package org.ureca.pinggubackend.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyPageCancelResponse {
    private final Long memberId;
    private final Long recruitId;
}
