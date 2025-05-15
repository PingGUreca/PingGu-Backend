package org.ureca.pinggubackend.domain.mypage.service;

import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.mypage.dto.request.MyPageUpdateRequest;
import org.ureca.pinggubackend.domain.mypage.dto.response.*;

import java.util.List;

public interface MyPageService {
    MyPageResponse getMyPage(Member member);
    MyPageUpdateResponse editProfile(Member member, MyPageUpdateRequest request);
    MyPageDeleteResponse deleteMember(Member member);
    MyPageCancelResponse cancelApply(Long memberId, Long recruitId);
    List<MyApplyResponse> getMyApplies(Long memberId);
    List<MyLikeResponse> getMyLikes(Long memberId);
    List<MyRecruitResponse> getMyRecruits(Long memberId);
}
