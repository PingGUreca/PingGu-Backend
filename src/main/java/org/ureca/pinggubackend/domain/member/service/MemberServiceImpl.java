package org.ureca.pinggubackend.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;
import org.ureca.pinggubackend.global.exception.BaseException;
import org.ureca.pinggubackend.global.exception.common.CommonErrorCode;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements  MemberService{
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void deleteMember(long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> BaseException.of(CommonErrorCode.USER_NOT_FOUND));

        memberRepository.delete(member);
    }
}
