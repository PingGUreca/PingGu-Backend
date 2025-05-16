package org.ureca.pinggubackend.domain.recruit.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.member.enums.Racket;
import org.ureca.pinggubackend.domain.recruit.enums.RecruitStatus;

import java.time.LocalDate;

@Getter
@Builder
public class RecruitGetDto {

    private boolean isAuthor;

    private boolean isLike;

    private boolean isApplied;

    private Long memberId;

    private String userName;

    private String clubName;

    private String location;

    private LocalDate date;

    private Integer capacity;

    private Gender gender;

    private Level level;

    private Racket racket;

    private String title;

    private String document;

    private String chatUrl;

    private RecruitStatus status;
}
