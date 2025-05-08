package org.ureca.pinggubackend.domain.recruit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitPreviewListResponse;

import java.time.LocalDate;

public interface RecruitRepositoryCustom {
    Page<RecruitPreviewListResponse> getRecruitPreviewList(LocalDate date, String gu, Level level, Gender gender, Pageable pageable);
}
