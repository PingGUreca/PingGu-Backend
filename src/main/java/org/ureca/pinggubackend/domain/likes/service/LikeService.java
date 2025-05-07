package org.ureca.pinggubackend.domain.likes.service;

import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitResponse;

import java.util.List;

public interface LikeService {
    List<RecruitResponse> getLikesByMemberId(Long memberId);
}