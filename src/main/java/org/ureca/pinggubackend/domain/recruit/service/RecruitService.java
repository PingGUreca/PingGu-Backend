package org.ureca.pinggubackend.domain.recruit.service;

import org.ureca.pinggubackend.domain.mypage.dto.response.MyRecruitResponse;
import org.ureca.pinggubackend.domain.recruit.dto.RecruitGetDto;
import org.ureca.pinggubackend.domain.recruit.dto.RecruitPostDto;
import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitResponse;

import java.util.List;

public interface RecruitService {

    Long postRecruit(RecruitPostDto recruitPostDto);
    List<MyRecruitResponse> getRecruitListByMemberId(Long memberId);
    RecruitGetDto getRecruit(Long recruitId);
}
