package org.ureca.pinggubackend.domain.mypage.dto.response;

import lombok.*;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.member.enums.MainHand;
import org.ureca.pinggubackend.domain.member.enums.Racket;

@Getter
@AllArgsConstructor
public class MyProfileResponse {
    private final String name;
    private final String gender;
    private final String gu;
    private final String level;
    private final String mainHand;
    private final String racket;

    public static MyProfileResponse from(Member member) {
        return new MyProfileResponse(
                member.getName(),
                member.getGender().toString(),
                member.getGu(),
                member.getLevel().toString(),
                member.getMainHand().toString(),
                member.getRacket().toString()
        );
    }
}
