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
import org.ureca.pinggubackend.domain.recruit.enums.RecruitStatus;
import org.ureca.pinggubackend.domain.recruit.repository.RecruitRepository;
import org.ureca.pinggubackend.global.exception.BaseException;
import org.ureca.pinggubackend.global.exception.common.CommonErrorCode;
import org.ureca.pinggubackend.global.exception.recruit.RecruitErrorCode;
import org.ureca.pinggubackend.global.exception.recruit.RecruitException;

import java.util.List;
import java.util.stream.Collectors;

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
    public void proceedApply(Member member, Long recruitId) {
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> RecruitException.of(RECRUIT_NOT_FOUND));

        // 비즈니스 규칙 검증
        if (recruit.getCurrent() >= recruit.getCapacity()) {
            throw RecruitException.of(RecruitErrorCode.RECRUIT_ALREADY_FULL);
        }

        if (recruit.getStatus() != RecruitStatus.OPEN) {
            throw RecruitException.of(RecruitErrorCode.RECRUIT_NOT_OPEN);
        }

        // 추가 비즈니스 검증 (해당 회원이 이미 지원했는지 등)
        if (applyRepository.findByMemberIdAndRecruitId(member.getId(), recruitId).isPresent()) {
            throw RecruitException.of(RecruitErrorCode.RECRUIT_ALREADY_APPLY);
        }

        // 지원 생성 및 상태 업데이트
        Apply apply = new Apply(member, recruit);
        applyRepository.save(apply);

        // 엔티티 내부에서 상태 관리 로직 실행
        recruit.incrementApply();
        recruitRepository.save(recruit);
    }

    @Override
    @Transactional
    public void cancelApply(Long memberId, Long recruitId) {
        Apply apply = applyRepository.findByMemberIdAndRecruitId(memberId, recruitId)
                .orElseThrow(() -> BaseException.of(CommonErrorCode.APPLY_NOT_FOUND));

        Recruit recruit = apply.getRecruit();

        // 취소 가능 여부 검증 (예: 모집이 이미 마감되었거나 특정 상태인 경우 취소 불가)
        if (recruit.getStatus() == RecruitStatus.CLOSED || recruit.getStatus() == RecruitStatus.EXPIRED) {
            throw RecruitException.of(RecruitErrorCode.RECRUIT_CANT_APPLY_CANCEL);
        }

        // 지원 삭제 및 상태 업데이트
        applyRepository.delete(apply);

        // 엔티티 내부에서 상태 관리 로직 실행
        recruit.decrementApply();
        recruitRepository.save(recruit);
    }
}
