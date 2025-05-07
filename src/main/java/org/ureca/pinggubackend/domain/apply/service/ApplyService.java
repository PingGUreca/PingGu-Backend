package org.ureca.pinggubackend.domain.apply.service;

import org.ureca.pinggubackend.domain.mypage.dto.response.MyApplyResponse;
import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitResponse;

import java.util.List;

public interface ApplyService {
    List<MyApplyResponse> getApplyListByMemberId(Long memberId);
}
