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

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;
    private final ApplyRepository applyRepository;

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
}