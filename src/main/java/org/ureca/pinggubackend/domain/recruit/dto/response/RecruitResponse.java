package org.ureca.pinggubackend.domain.recruit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.ureca.pinggubackend.domain.location.entity.Club;
import org.ureca.pinggubackend.domain.recruit.entity.Recruit;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@AllArgsConstructor
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


    public static RecruitResponse from(Recruit recruit) {
        return new RecruitResponse(
                recruit.getId(),
                recruit.getTitle(),
                List.of(recruit.getClub().getName(), recruit.getClub().getAddress()),
                recruit.getClub().getGu(),
                recruit.getDate().toString(),
                recruit.getCapacity(),
                recruit.getCurrent(),
                recruit.getGender().toString(),
                recruit.getLevel().toString(),
                recruit.getRacket().toString(),
                recruit.getDocument(),
                recruit.getChatUrl()
        );
    }
}
