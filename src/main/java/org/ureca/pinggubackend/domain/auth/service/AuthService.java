package org.ureca.pinggubackend.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ureca.pinggubackend.domain.auth.api.KakaoProfileApi;
import org.ureca.pinggubackend.domain.auth.api.KakaoTokenApi;
import org.ureca.pinggubackend.domain.auth.dto.*;
import org.ureca.pinggubackend.domain.auth.entity.RefreshToken;
import org.ureca.pinggubackend.domain.auth.jwt.JwtUtil;
import org.ureca.pinggubackend.domain.auth.repository.RefreshTokenRepository;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.member.enums.MainHand;
import org.ureca.pinggubackend.domain.member.enums.Racket;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;
import org.ureca.pinggubackend.domain.member.service.MemberService;
import org.ureca.pinggubackend.global.exception.BaseException;
import org.ureca.pinggubackend.global.exception.common.CommonErrorCode;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final KakaoProfileApi kakaoProfileApi;
    private final KakaoTokenApi kakaoTokenApi;

    private String getKakaoAccessToken(String code) {
        return kakaoTokenApi.requestAccessToken(code);
    }

    private KakaoUserProfileResponse getUserProfileFromKakao(String code) {
        String kakaoAccessToken = getKakaoAccessToken(code);
        return kakaoProfileApi.getUserProfile(kakaoAccessToken);
    }

    private void saveRefreshToken(Long memberId, String refreshToken) {
        refreshTokenRepository.findByMemberId(memberId)
                .ifPresentOrElse(
                        existing -> existing.updateToken(refreshToken),
                        () -> refreshTokenRepository.save(new RefreshToken(memberId, refreshToken))
                );
    }

    public KakaoLoginResponse loginOrRegister(String code) {
        KakaoUserProfileResponse kakaoUserProfile = getUserProfileFromKakao(code);

        Long kakaoId = kakaoUserProfile.getId();
        Optional<Member> optionalMember = memberService.findByKakaoId(kakaoId);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            String accessToken = jwtUtil.createAccessToken(member.getId());
            String refreshToken = jwtUtil.createRefreshToken(member.getId());

            saveRefreshToken(member.getId(), refreshToken);

            return new KakaoLoginResponse(
                    member.getId(),
                    accessToken,
                    refreshToken,
                    true
            );
        } else {
            String tmpName = kakaoUserProfile.getKakaoAccount().getProfile().getNickname();
            String tmpEmail = kakaoUserProfile.getKakaoAccount().getEmail();

            Member newMember = Member.builder()
                    .kakaoId(kakaoId)
                    .email(tmpEmail)
                    .name(tmpName)
                    .phoneNumber("000-0000-0000")
                    .gu("tmp")
                    .gender(Gender.ALL)
                    .level(Level.BEGINNER)
                    .mainHand(MainHand.L)
                    .racket(Racket.PEN_HOLDER)
                    .build();

            Long tmpMemberId = memberRepository.save(newMember).getId();

            return new KakaoLoginResponse(
                    tmpMemberId,
                    null,
                    null,
                    false
            );
        }
    }


    // Access Token이 만료된 경우 Refresh토큰도 같이 재발급하도록 수정
    public JwtReissueResponse reissueAccessToken(String refreshToken) {
        if (jwtUtil.isExpired(refreshToken)) {
            throw BaseException.of(CommonErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        Long memberId = jwtUtil.getMemberId(refreshToken);

        String newAccessToken = jwtUtil.createAccessToken(memberId);
        String newRefreshToken = jwtUtil.createRefreshToken(memberId);

        saveRefreshToken(memberId, newRefreshToken);

        return new JwtReissueResponse(newAccessToken, newRefreshToken);
    }


    public SignupResponse signup(SignupRequest request) {
        memberService.updateBasicInfo(
                request.memberId(),
                request.name(),
                request.gender(),
                request.mainHand(),
                request.racket(),
                request.gu(),
                request.level()
        );

        String accessToken = jwtUtil.createAccessToken(request.memberId());
        String refreshToken = jwtUtil.createRefreshToken(request.memberId());

        saveRefreshToken(request.memberId(), refreshToken);

        return new SignupResponse(request.memberId(), accessToken, refreshToken);
    }

    public LogoutResponse logout(String refreshToken) {
        if (jwtUtil.isExpired(refreshToken)) {
            throw BaseException.of(CommonErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        Long memberId = jwtUtil.getMemberId(refreshToken);

        refreshTokenRepository.findByToken(refreshToken)
                .ifPresent(refreshTokenRepository::delete);

        return new LogoutResponse(memberId);
    }
}