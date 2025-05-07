package org.ureca.pinggubackend.domain.member.service;

import org.ureca.pinggubackend.domain.mypage.dto.request.MyProfileUpdate;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyProfileResponse;

public interface MemberService {
    MyProfileResponse updateMyProfile(Long memberId, MyProfileUpdate request);
}
