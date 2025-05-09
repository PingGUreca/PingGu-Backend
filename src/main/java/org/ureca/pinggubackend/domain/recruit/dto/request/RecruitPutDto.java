package org.ureca.pinggubackend.domain.recruit.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.member.enums.Racket;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RecruitPutDto {

    @NotNull
    private Long clubId;

    @NotNull
    private LocalDate date;

    @NotNull
    private Gender gender;

    @NotNull
    private Level level;

    @NotNull
    private Racket racket;

    @NotEmpty
    private String chatUrl;

    @NotEmpty
    private String title;

    @NotNull
    private String document;

}
