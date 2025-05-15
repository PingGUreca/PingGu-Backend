package org.ureca.pinggubackend.domain.apply.service;

import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyApplyResponse;

import java.util.List;

public interface ApplyService {
    List<MyApplyResponse> getMyApplies(Long memberId);
    void cancelApply(Long memberId, Long recruitId);
    void proceedApply(Member member, Long recruitId);
}
