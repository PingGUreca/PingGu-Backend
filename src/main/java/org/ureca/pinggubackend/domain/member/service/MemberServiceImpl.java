package org.ureca.pinggubackend.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.member.enums.MainHand;
import org.ureca.pinggubackend.domain.member.enums.Racket;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;
import org.ureca.pinggubackend.global.exception.BaseException;
import org.ureca.pinggubackend.global.exception.common.CommonErrorCode;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void deleteMember(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> BaseException.of(CommonErrorCode.USER_NOT_FOUND));

        memberRepository.delete(member);
    }

    @Override
    public void updateBasicInfo(long memberId, String name, Gender gender, MainHand mainHand,
                                Racket racket, String gu, Level level) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> BaseException.of(CommonErrorCode.USER_NOT_FOUND));

        member.updateProfile(name, gender, gu, level, mainHand, racket);

        memberRepository.save(member);
    }

    @Override
    public Member findById(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }

    @Override
    public Optional<Member> findByKakaoId(long kakaoId) {
        Optional<Member> member = memberRepository.findByKakaoId(kakaoId);
        return member;
    }
}
