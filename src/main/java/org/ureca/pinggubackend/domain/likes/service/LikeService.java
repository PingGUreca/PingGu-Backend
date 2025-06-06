package org.ureca.pinggubackend.domain.likes.service;

import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyLikeResponse;

import java.util.List;

public interface LikeService {
    List<MyLikeResponse> getLikedRecruitList(Long memberId);
    boolean toggleLike(Member member, Long recruitId);
}