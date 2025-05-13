package org.ureca.pinggubackend.domain.apply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ureca.pinggubackend.domain.apply.entity.Apply;
import org.ureca.pinggubackend.domain.apply.repository.ApplyRepository;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyApplyResponse;
import org.ureca.pinggubackend.domain.recruit.entity.Recruit;
import org.ureca.pinggubackend.domain.recruit.repository.RecruitRepository;
import org.ureca.pinggubackend.global.exception.BaseException;
import org.ureca.pinggubackend.global.exception.common.CommonErrorCode;

import java.util.List;
import java.util.stream.Collectors;

import static org.ureca.pinggubackend.global.exception.common.CommonErrorCode.USER_NOT_FOUND;
import static org.ureca.pinggubackend.global.exception.recruit.RecruitErrorCode.RECRUIT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ApplyServiceImpl implements ApplyService{

    private final ApplyRepository applyRepository;
    private final MemberRepository memberRepository;
    private final RecruitRepository recruitRepository;

    @Override
    public List<MyApplyResponse> getMyApplies(Long memberId) {

        List<Apply> applies = applyRepository.findByMemberId(memberId);

        return applies.stream()
                .map(apply -> MyApplyResponse.from(apply.getRecruit()))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void cancelApply(Long memberId, Long recruitId){
        Apply apply = applyRepository.findByMemberIdAndRecruitId(memberId, recruitId)
                .orElseThrow(() -> BaseException.of(CommonErrorCode.APPLY_NOT_FOUND));

        applyRepository.delete(apply);
    }

    @Override
    @Transactional
    public void proceedApply(Long memberId, Long recruitId) {
        //TODO : 사용자 정보 있을 경우, memberId 수정
        Member member = memberRepository.findById(memberId).orElseThrow(() -> BaseException.of(USER_NOT_FOUND));
        Recruit recruit = recruitRepository.findById(recruitId).orElseThrow(() -> BaseException.of(RECRUIT_NOT_FOUND));

        Apply apply = new Apply(member, recruit);

        applyRepository.save(apply);
    }
}
