package org.ureca.pinggubackend.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ureca.pinggubackend.domain.recruit.entity.Recruit;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class MyRecruitResponse {
    private final Long id;
    private final String title;
    private final String date;

    public static MyRecruitResponse from(Recruit recruit) {
        return new MyRecruitResponse(
                recruit.getId(),
                recruit.getTitle(),
                recruit.getDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
        );
    }
}
