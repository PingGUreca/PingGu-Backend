package org.ureca.pinggubackend.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ureca.pinggubackend.domain.apply.entity.Apply;
import org.ureca.pinggubackend.domain.apply.repository.ApplyRepository;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;
import org.ureca.pinggubackend.domain.mypage.dto.request.MyPageUpdateRequest;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyPageCancelResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyPageDeleteResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyPageUpdateResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyPageResponse;
import org.ureca.pinggubackend.global.exception.BaseException;
import org.ureca.pinggubackend.global.exception.common.CommonErrorCode;
import org.ureca.pinggubackend.domain.apply.service.ApplyService;
import org.ureca.pinggubackend.domain.likes.service.LikeService;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyApplyResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyLikeResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyProfileResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyRecruitResponse;
import org.ureca.pinggubackend.domain.recruit.service.RecruitService;
import org.ureca.pinggubackend.global.exception.BaseException;

import java.util.List;

import static org.ureca.pinggubackend.global.exception.common.CommonErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;
    private final ApplyRepository applyRepository;
    private final RecruitService recruitService;
    private final ApplyService applyService;
    private final LikeService likeService;

    public MyPageResponse getMyPage(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> BaseException.of(CommonErrorCode.USER_NOT_FOUND));

        return MyPageResponse.from(member);
    }

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

    @Transactional
    public MyPageDeleteResponse deleteMember(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> BaseException.of(CommonErrorCode.USER_NOT_FOUND));

        memberRepository.delete(member);

        return new MyPageDeleteResponse(member.getId());
    }

    @Transactional
    public MyPageCancelResponse cancelApply(Long memberId, Long recruitId) {
        Apply apply = applyRepository.findByMemberIdAndRecruitId(memberId, recruitId)
                .orElseThrow(() -> BaseException.of(CommonErrorCode.APPLY_NOT_FOUND));

        applyRepository.delete(apply);

        return new MyPageCancelResponse(memberId,recruitId);
    }

    //TODO: 멤버 관련 커스템 예외 정의되면 해당 예외 수정
    public MyProfileResponse getMyPageInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> BaseException.of(USER_NOT_FOUND));
        return MyProfileResponse.from(member);
    }

    public List<MyApplyResponse> getMyApplies(Long memberId) {
        return applyService.getMyApplies(memberId);
    }

    public List<MyLikeResponse> getMyLikes(Long memberId) {
        return likeService.getLikedRecruitList(memberId);
    }

    public List<MyRecruitResponse> getMyRecruits(Long memberId) {
        return recruitService.getRecruitListByMemberId(memberId);
    }


}
