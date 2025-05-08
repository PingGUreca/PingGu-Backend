package org.ureca.pinggubackend.domain.recruit.repository;

import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitPreviewListResponse;

import java.time.LocalDate;
import java.util.List;

public interface RecruitRepositoryCustom {
    List<RecruitPreviewListResponse> getRecruitPreviewList(LocalDate date, String gu, Level level, Gender gender);
}
