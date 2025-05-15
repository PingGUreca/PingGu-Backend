package org.ureca.pinggubackend.domain.auth.dto;

import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.member.enums.MainHand;
import org.ureca.pinggubackend.domain.member.enums.Racket;

public record SignupRequest(
        long memberId,
        String name,
        Gender gender,
        MainHand mainHand,
        Racket racket,
        String gu,
        Level level
) {
}
