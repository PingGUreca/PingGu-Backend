package org.ureca.pinggubackend.domain.recruit.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyRecruitResponse;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitGetDto;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitPostDto;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitPutDto;
import org.ureca.pinggubackend.domain.recruit.dto.response.ApplyResponse;
import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitPreviewListResponse;

import java.time.LocalDate;
import java.util.List;

public interface RecruitService {

    Long postRecruit(Member member, RecruitPostDto recruitPostDto);
    List<MyRecruitResponse> getRecruitListByMemberId(Long memberId);
    RecruitGetDto getRecruitWithNonMember(Long recruitId);
    RecruitGetDto getRecruitWithMember(Member member, Long recruitId);
    void putRecruit(Long memberId, Long recruitId, RecruitPutDto recruitPutDto);
    void deleteRecruit(Long memberId, Long recruitId);
    Page<RecruitPreviewListResponse> getRecruitPreviewList(LocalDate date, String gu, Level level, Gender gender, Pageable pageable);
    boolean toggleLike(Member member, Long recruitId);
    ApplyResponse proceedApply(Member member, Long recruitId);
    ApplyResponse cancelApply(Long memberId, Long recruitId);
}
