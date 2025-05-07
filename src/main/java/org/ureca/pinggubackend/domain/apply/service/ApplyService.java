package org.ureca.pinggubackend.domain.apply.service;

import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitResponse;

import java.util.List;

public interface ApplyService {
    List<RecruitResponse> getLikesByMemberId(Long memberId);
}
