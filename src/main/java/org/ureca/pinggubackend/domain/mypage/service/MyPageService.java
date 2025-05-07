package org.ureca.pinggubackend.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyProfileResponse;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;

    //TODO: 멤버 관련 커스템 예외 정의되면 해당 예외 수정
    public MyProfileResponse getMyPageInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()->new IllegalArgumentException("아따 사람이없당께"));
        return MyProfileResponse.from(member);
    }
}
