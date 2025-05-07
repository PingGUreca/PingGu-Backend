package org.ureca.pinggubackend.domain.recruit.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.member.enums.Racket;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RecruitPutDto {

    private Long clubId;

    private LocalDate date;

    private Gender gender;

    private Level level;

    private Racket racket;

    private String title;

    private String document;

    private String chatUrl;
}
