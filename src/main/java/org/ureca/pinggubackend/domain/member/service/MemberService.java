package org.ureca.pinggubackend.domain.member.service;

import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.member.enums.MainHand;
import org.ureca.pinggubackend.domain.member.enums.Racket;

import java.util.Optional;

public interface MemberService {
    void deleteMember(Member member);

    void updateBasicInfo(Long memberId, String name, Gender gender, MainHand mainHand,
                         Racket racket, String gu, Level level);

    Member findById(long memberId);

    Optional<Member> findByKakaoId(long kakaoId);
}
