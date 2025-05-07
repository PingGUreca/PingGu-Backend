package org.ureca.pinggubackend.domain.recruit.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.ureca.pinggubackend.domain.location.entity.Club;
import org.ureca.pinggubackend.domain.recruit.entity.Recruit;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class RecruitResponse {
    private final Long id;
    private final String title;
    private final List<String> club;
    private final String gu;
    private final String date;
    private final Integer capacity;
    private final Integer current;
    private final String gender;
    private final String level;
    private final String racket;
    private final String document;
    private final String chatUrl;

    @Builder
    public RecruitResponse(Long id, String title, List<String> club, String gu, String date, Integer capacity, Integer current, String gender, String level, String racket, String document, String chatUrl) {
        this.id = id;
        this.title = title;
        this.club = club;
        this.gu = gu;
        this.date = date;
        this.capacity = capacity;
        this.current = current;
        this.gender = gender;
        this.level = level;
        this.racket = racket;
        this.document = document;
        this.chatUrl = chatUrl;
    }

    public static RecruitResponse from(Recruit recruit) {
        return RecruitResponse.builder()
                .id(recruit.getId())
                .title(recruit.getTitle())
                .club(List.of(recruit.getClub().getName(), recruit.getClub().getAddress()))
                .gu(recruit.getClub().getGu())
                .date(recruit.getDate().format(DateTimeFormatter.ISO_DATE))
                .capacity(recruit.getCapacity())
                .current(recruit.getCurrent())
                .gender(recruit.getGender().toString())
                .level(recruit.getLevel().toString())
                .racket(recruit.getRacket().toString())
                .document(recruit.getDocument())
                .chatUrl(recruit.getChatUrl())
                .build();
    }
}
