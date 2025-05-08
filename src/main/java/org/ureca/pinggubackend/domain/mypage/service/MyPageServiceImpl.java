package org.ureca.pinggubackend.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;
import org.ureca.pinggubackend.domain.member.service.MemberService;
import org.ureca.pinggubackend.domain.member.service.MemberServiceImpl;
import org.ureca.pinggubackend.domain.mypage.dto.request.MyPageUpdateRequest;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyPageCancelResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyPageDeleteResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyPageUpdateResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyPageResponse;
import org.ureca.pinggubackend.global.exception.BaseException;
import org.ureca.pinggubackend.global.exception.common.CommonErrorCode;
import org.ureca.pinggubackend.domain.apply.service.ApplyService;
import org.ureca.pinggubackend.domain.likes.service.LikeService;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyApplyResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyLikeResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyRecruitResponse;
import org.ureca.pinggubackend.domain.recruit.service.RecruitService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MemberRepository memberRepository;
    private final RecruitService recruitService;
    private final ApplyService applyService;
    private final LikeService likeService;
    private final MemberService memberService;

    @Override
    public MyPageResponse getMyPage(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> BaseException.of(CommonErrorCode.USER_NOT_FOUND));

        return MyPageResponse.from(member);
    }

    @Override
    @Transactional
    public MyPageUpdateResponse editProfile(long id, MyPageUpdateRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(()->BaseException.of(CommonErrorCode.USER_NOT_FOUND));

        member.updateProfile(
                request.getName(),
                request.getGender(),
                request.getGu(),
                request.getLevel(),
                request.getMainHand(),
                request.getRacket()
        );

        return new MyPageUpdateResponse(member.getId());
    }

    @Override
    public MyPageDeleteResponse deleteMember(long memberId) {
        memberService.deleteMember(memberId);
        return new MyPageDeleteResponse(memberId);
    }

    @Override
    public MyPageCancelResponse cancelApply(Long memberId, Long recruitId) {
        applyService.cancelApply(memberId,recruitId);
        return new MyPageCancelResponse(memberId,recruitId);
    }

    @Override
    public List<MyApplyResponse> getMyApplies(Long memberId) {
        return applyService.getMyApplies(memberId);
    }

    @Override
    public List<MyLikeResponse> getMyLikes(Long memberId) {
        return likeService.getLikedRecruitList(memberId);
    }

    @Override
    public List<MyRecruitResponse> getMyRecruits(Long memberId) {
        return recruitService.getRecruitListByMemberId(memberId);
    }
}
