package org.ureca.pinggubackend.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ureca.pinggubackend.domain.recruit.entity.Recruit;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@AllArgsConstructor
public class MyApplyResponse {
    private final Long id;
    private final String title;
    private final List<String> club;
    private final String date;
    private final String chatUrl;
    private final String status;


    public static MyApplyResponse from(Recruit recruit) {
        return new MyApplyResponse(
                recruit.getId(),
                recruit.getTitle(),
                List.of(recruit.getClub().getName(), recruit.getClub().getAddress()),
                recruit.getDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")),
                recruit.getChatUrl(),
                recruit.getStatus().toString()
        );
    }
}
