package org.ureca.pinggubackend.domain.recruit.dto;

import lombok.Builder;
import lombok.Getter;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.member.enums.Racket;

import java.time.LocalDate;

@Getter
@Builder
public class RecruitGetDto {

    private Long userId;

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
}
