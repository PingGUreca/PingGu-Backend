package org.ureca.pinggubackend.domain.recruit.service;

import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitGetDto;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitPostDto;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitPutDto;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyRecruitResponse;

import java.util.List;

public interface RecruitService {

    Long postRecruit(RecruitPostDto recruitPostDto);
    List<MyRecruitResponse> getRecruitListByMemberId(Long memberId);
    RecruitGetDto getRecruit(Long recruitId);
    void putRecruit(Long recruitId, RecruitPutDto recruitPutDto);
    void deleteRecruit(Long recruitId);
}
