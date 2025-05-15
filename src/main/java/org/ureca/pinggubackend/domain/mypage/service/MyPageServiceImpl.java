package org.ureca.pinggubackend.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ureca.pinggubackend.domain.apply.service.ApplyService;
import org.ureca.pinggubackend.domain.likes.service.LikeService;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;
import org.ureca.pinggubackend.domain.member.service.MemberService;
import org.ureca.pinggubackend.domain.mypage.dto.request.MyPageUpdateRequest;
import org.ureca.pinggubackend.domain.mypage.dto.response.*;
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
    public MyPageResponse getMyPage(Member member) {
        return MyPageResponse.from(member);
    }

    @Override
    @Transactional
    public MyPageUpdateResponse editProfile(Member member, MyPageUpdateRequest request) {
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
    public MyPageDeleteResponse deleteMember(Member member) {
        memberService.deleteMember(member);
        return new MyPageDeleteResponse(member.getId());
    }

    @Override
    public MyPageCancelResponse cancelApply(Long memberId, Long recruitId) {
        applyService.cancelApply(memberId, recruitId);
        return new MyPageCancelResponse(memberId, recruitId);
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
