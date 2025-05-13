package org.ureca.pinggubackend.domain.recruit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplyResponse {
    private final Long memberId;
    private final Long recruitId;
}