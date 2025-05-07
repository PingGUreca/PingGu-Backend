package org.ureca.pinggubackend.domain.recruit.service;

import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitResponse;

import java.util.List;

public interface RecruitService {
    List<RecruitResponse> getRecruitsByMemberId(Long memberId);
}
