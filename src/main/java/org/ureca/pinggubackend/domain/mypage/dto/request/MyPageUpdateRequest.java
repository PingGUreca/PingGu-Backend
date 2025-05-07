package org.ureca.pinggubackend.domain.mypage.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.member.enums.MainHand;
import org.ureca.pinggubackend.domain.member.enums.Racket;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageUpdateRequest {
    private String name;
    private Gender gender;
    private String gu;
    private Level level;
    private MainHand mainHand;
    private Racket racket;
}
