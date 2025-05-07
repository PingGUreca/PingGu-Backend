package org.ureca.pinggubackend.domain.mypage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ureca.pinggubackend.domain.recruit.entity.Recruit;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@AllArgsConstructor
public class MyLikeResponse {
    private final Long id;
    private final String title;
    private final String date;

    public static MyLikeResponse from(Recruit recruit) {
        return new MyLikeResponse(
                recruit.getId(),
                recruit.getTitle(),
                recruit.getDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
        );
    }
}
