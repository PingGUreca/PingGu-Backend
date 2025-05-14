package org.ureca.pinggubackend.domain.recruit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ureca.pinggubackend.domain.recruit.enums.RecruitStatus;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RecruitPreviewListResponse {
    private final Long recruitId;
    private final String clubName;
    private final LocalDate date;
    private final String title;
    private final Integer capacity;
    private final Integer current;
    private final RecruitStatus status;
}
