package org.ureca.pinggubackend.domain.apply.service;

import org.ureca.pinggubackend.domain.mypage.dto.response.MyApplyResponse;

import java.util.List;

public interface ApplyService {
    List<MyApplyResponse> getMyApplies(Long memberId);
}
