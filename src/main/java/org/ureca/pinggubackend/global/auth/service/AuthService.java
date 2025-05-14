package org.ureca.pinggubackend.global.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.member.enums.MainHand;
import org.ureca.pinggubackend.domain.member.enums.Racket;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;
import org.ureca.pinggubackend.domain.member.service.MemberService;
import org.ureca.pinggubackend.global.auth.dto.KakaoLoginResponse;
import org.ureca.pinggubackend.global.auth.dto.KakaoUserProfileResponse;
import org.ureca.pinggubackend.global.auth.dto.SignupRequest;
import org.ureca.pinggubackend.global.auth.entity.RefreshToken;
import org.ureca.pinggubackend.global.auth.jwt.JwtUtil;
import org.ureca.pinggubackend.global.auth.repository.RefreshTokenRepository;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${spring.kakao.auth.client-id}")
    private String clientId;

    @Value("${spring.kakao.auth.redirect-uri}")
    private String redirectUri;

    public KakaoLoginResponse loginOrRegister(String code) {
        KakaoUserProfileResponse user = getUserProfile(code);
        String email = user.getKakaoAccount().getEmail();
        String nickname = user.getKakaoAccount().getProfile().getNickname();
        Optional<Member> member = memberService.findByEmail(user.getKakaoAccount().getEmail());

        // findByEmail에서 null을 반환하게 해야겠다.
        if (member.isPresent()) {
            // 사후 관리
            String accessToken = jwtUtil.createAccessToken(email);
            String refreshToken = jwtUtil.createRefreshToken(email);

            // refreshToken DB 저장
            refreshTokenRepository.findByEmail(email)
            .ifPresentOrElse(
                            existing -> existing.updateToken(refreshToken),
                    () -> refreshTokenRepository.save(
                            new RefreshToken(email, refreshToken)
                    )
                    );

            return new KakaoLoginResponse(
                    accessToken,
                    refreshToken,
                    true,
                    null
            );

        }else{
            // 위에서 받은 member정보로 일단 불완전한 db저장
            Member kakaoMember = new Member();
            kakaoMember.setEmail(email);
            kakaoMember.setName(nickname);
            kakaoMember.setPhoneNumber("000-0000-0000");
            kakaoMember.setGu("몰라");
            kakaoMember.setGender(Gender.ALL);
            kakaoMember.setLevel(Level.BEGINNER);
            kakaoMember.setMainHand(MainHand.L);
            kakaoMember.setRacket(Racket.PEN_HOLDER);

            Long unStableid = memberRepository.save(kakaoMember).getId();
            //프론트에 id
            return new KakaoLoginResponse(
                    null,
                    null,
                    false,
                    unStableid
            );

        }

        // 여기서 기존 회원인지 아닌지를 판단해서 내가 계속 말했던 KakaoLoginResponse를 반환하면 됨
        // 기존 회원이면 토큰들 다 주고 isRegister=true
        // 신규 회원이면 토큰 주지 말고 IsRegister=false
    }

    public KakaoUserProfileResponse getUserProfile(String code) {
        // 1. Access Token 요청
        String kakaoAccessToken = requestAccessToken(code);
        System.out.println("kakaoAccessToken = " + kakaoAccessToken);

        // 2. 사용자 정보 요청
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(kakaoAccessToken);

        HttpEntity<String> entity = new HttpEntity<>(null,headers);

        ResponseEntity<KakaoUserProfileResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                KakaoUserProfileResponse.class
        );

        // 카카오에서 가져온 유저 정보를 KakaoUserResponse클래스 즉, dto에 담아서 반환
        return response.getBody();
    }

    private String requestAccessToken(String code) {
        // RestTemplate 사용
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://kauth.kakao.com/oauth/token",
                request,
                String.class
        );

        // access_token 추출
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.getBody());
            return node.get("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("토큰 파싱 실패", e);
        }
    }

    public ResponseEntity<?> reissueAccessToken(String refreshToken) {

        if (jwtUtil.isExpired(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired");
        }

        String email = jwtUtil.getEmail(refreshToken);
        String newAccessToken = jwtUtil.createAccessToken(email);

        return ResponseEntity.ok(Map.of("new_token",  newAccessToken));
    }


    public ResponseEntity<?> signup(SignupRequest request) {
        // 정보 저장
        memberService.updateBasicInfo(request.memberId(), request.name(), request.gender(),
                request.mainHand(), request.racket(), request.gu(), request.level());

        // 토큰 발급
        Member member = memberService.findById(request.memberId());
        String email = member.getEmail();

        String accessToken = jwtUtil.createAccessToken(email);
        String refreshToken = jwtUtil.createRefreshToken(email);

        // db 에 저장하는 토큰 확인
        System.out.println("🧪 db에 저장할 refreshToken: " + refreshToken);

        // refreshToken DB 저장
        refreshTokenRepository.findByEmail(email)
                .ifPresentOrElse(
                        existing -> existing.updateToken(refreshToken),
                        () -> refreshTokenRepository.save(
                                new RefreshToken(email, refreshToken)
                        )
                );


        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .maxAge(Duration.ofDays(14))
                .build();

        // 프론트에 반환하는 토큰 확인
        System.out.println("🧪 프론트에 반환하는 refreshToken: " + refreshToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(null);
    }

    public ResponseEntity<?> logout(String refreshToken) {
        if (jwtUtil.isExpired(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("token expired");
        }

        System.out.println("🧪 쿠키에서 가져온 refreshToken: " + refreshToken);
        refreshTokenRepository.findByToken(refreshToken)
                .ifPresentOrElse(
                        token -> {
                            refreshTokenRepository.delete(token);
                            System.out.println("✅ 토큰 삭제됨");
                        },
                        () -> {
                            System.out.println("❌ 해당 토큰이 DB에 없음");
                        }
                );

        // 쿠키 무효화
        ResponseCookie deleteCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body("logout success");
    }
}