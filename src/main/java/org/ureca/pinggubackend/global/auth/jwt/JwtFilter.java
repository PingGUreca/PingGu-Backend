package org.ureca.pinggubackend.global.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.ureca.pinggubackend.domain.member.dto.CustomMemberDetails;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;

import java.io.IOException;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    public JwtFilter(JwtUtil jwtUtil, MemberRepository memberRepository) {
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        System.out.println("🔥 JwtFilter 실행됨: " + uri);

        // uri 에 있는 요청은 토큰 검증하지 않고 통과
        List<String> whitelist = List.of("/auth/kakao-login", "/auth/signup", "/auth/token");

        if (whitelist.contains(uri)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            // 조건에 해당 되면 메소드 종료
            return;
        }

        System.out.println("authorization now");
        // Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        // 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            System.out.println("token expired");

            // 쿠키에서 refresh_token 가져오기
            String refreshToken = null;
            if (request.getCookies() != null) {
                for (var cookie : request.getCookies()) {
                    if (cookie.getName().equals("refresh_token")) {
                        refreshToken = cookie.getValue();
                    }
                }
            }

            if (refreshToken != null && !jwtUtil.isExpired(refreshToken)) {
                String email = jwtUtil.getEmail(refreshToken);
                String newAccessToken = jwtUtil.createAccessToken(email);
                response.setHeader("Authorization", "Bearer " + newAccessToken);

                // 인증 등록
                Member member = memberRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
                CustomMemberDetails userDetails = new CustomMemberDetails(member);
                Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);

                filterChain.doFilter(request, response);
                return;
            } else {
                // refresh 토큰도 없거나 만료됐을시
                // doFilter로 가지 말고, loginOrRegister로 가도록 하기.
                response.sendRedirect("/auth/kakao-login");
                return;
            }
        }

        // 토큰에서 email 획득
        String email = jwtUtil.getEmail(token);
        System.out.println("👾 email: " + email);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));

        // memberDetails에 회원 정보 담기
        CustomMemberDetails customMemberDetails = new CustomMemberDetails(member);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customMemberDetails.getId(),
                null, customMemberDetails.getAuthorities());

        // 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 다음 필터에 전달함 => filterChain
        filterChain.doFilter(request, response);

    }
}
