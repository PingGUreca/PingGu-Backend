package org.ureca.pinggubackend.domain.recruit.service;

import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitGetDto;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitPostDto;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitPutDto;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyRecruitResponse;
import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitPreviewListResponse;

import java.time.LocalDate;
import java.util.List;

public interface RecruitService {

    Long postRecruit(RecruitPostDto recruitPostDto);
    List<MyRecruitResponse> getRecruitListByMemberId(Long memberId);
    RecruitGetDto getRecruit(Long recruitId);
    void putRecruit(Long recruitId, RecruitPutDto recruitPutDto);
    void deleteRecruit(Long recruitId);
    List<RecruitPreviewListResponse> getRecruitPreviewList(LocalDate date, String gu, Level level, Gender gender);
}
