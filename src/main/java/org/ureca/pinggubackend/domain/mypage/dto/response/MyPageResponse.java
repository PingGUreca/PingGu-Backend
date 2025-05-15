package org.ureca.pinggubackend.domain.mypage.dto.response;

import lombok.*;
import org.ureca.pinggubackend.domain.member.entity.Member;

@Getter
@AllArgsConstructor
public class MyPageResponse {
    private final String name;
    private final String gender;
    private final String gu;
    private final String level;
    private final String mainHand;
    private final String racket;
    private final String profileImgUrl;

    public static MyPageResponse from(Member member) {
        return new MyPageResponse(
                member.getName(),
                member.getGender().toString(),
                member.getGu(),
                member.getLevel().toString(),
                member.getMainHand().toString(),
                member.getRacket().toString(),
                member.getProfileImgUrl()
        );
    }
}
