package org.ureca.pinggubackend.domain.likes.service;

import org.ureca.pinggubackend.domain.mypage.dto.response.MyLikeResponse;
import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitResponse;

import java.util.List;

public interface LikeService {
    List<MyLikeResponse> getLikeListByMemberId(Long memberId);
}