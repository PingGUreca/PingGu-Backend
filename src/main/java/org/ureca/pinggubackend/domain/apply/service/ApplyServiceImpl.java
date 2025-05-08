package org.ureca.pinggubackend.domain.apply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ureca.pinggubackend.domain.apply.entity.Apply;
import org.ureca.pinggubackend.domain.apply.repository.ApplyRepository;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyApplyResponse;
import org.ureca.pinggubackend.global.exception.BaseException;
import org.ureca.pinggubackend.global.exception.common.CommonErrorCode;

import java.util.List;
import java.util.stream.Collectors;

import static org.ureca.pinggubackend.global.exception.common.CommonErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ApplyServiceImpl implements ApplyService{

    private final ApplyRepository applyRepository;

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
}
