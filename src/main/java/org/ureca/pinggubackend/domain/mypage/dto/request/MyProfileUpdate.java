package org.ureca.pinggubackend.domain.mypage.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.member.enums.MainHand;
import org.ureca.pinggubackend.domain.member.enums.Racket;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyProfileUpdate {
    private String name;
    private Level level;
    private MainHand mainHand;
    private Racket racket;
}
