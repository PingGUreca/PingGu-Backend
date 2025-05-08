package org.ureca.pinggubackend.domain.mypage.service;

import org.ureca.pinggubackend.domain.mypage.dto.request.MyPageUpdateRequest;
import org.ureca.pinggubackend.domain.mypage.dto.response.*;

import java.util.List;

public interface MyPageService {
    MyPageResponse getMyPage(long memberId);
    MyPageUpdateResponse editProfile(long id, MyPageUpdateRequest request);
    MyPageDeleteResponse deleteMember(long memberId);
    MyPageCancelResponse cancelApply(Long memberId, Long recruitId);
    List<MyApplyResponse> getMyApplies(Long memberId);
    List<MyLikeResponse> getMyLikes(Long memberId);
    List<MyRecruitResponse> getMyRecruits(Long memberId);
}
