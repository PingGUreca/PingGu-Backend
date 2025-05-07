package org.ureca.pinggubackend.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ureca.pinggubackend.domain.apply.service.ApplyService;
import org.ureca.pinggubackend.domain.likes.service.LikeService;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyApplyResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyLikeResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyProfileResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyRecruitResponse;
import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitResponse;
import org.ureca.pinggubackend.domain.recruit.service.RecruitService;
import org.ureca.pinggubackend.global.exception.BaseException;

import java.util.List;

import static org.ureca.pinggubackend.global.exception.common.CommonErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;
    private final RecruitService recruitService;
    private final ApplyService applyService;
    private final LikeService likeService;

    //TODO: 멤버 관련 커스템 예외 정의되면 해당 예외 수정
    public MyProfileResponse getMyPageInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> BaseException.of(USER_NOT_FOUND));
        return MyProfileResponse.from(member);
    }

    public List<MyApplyResponse> getMyApplies(Long memberId) {
        return applyService.getApplyListByMemberId(memberId);
    }

    public List<MyLikeResponse> getMyLikes(Long memberId) {
        return likeService.getLikeListByMemberId(memberId);
    }

    public List<MyRecruitResponse> getMyRecruits(Long memberId) {
        return recruitService.getRecruitListByMemberId(memberId);
    }


}
