package org.ureca.pinggubackend.domain.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.ureca.pinggubackend.domain.member.dto.CustomMemberDetails;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;
import org.ureca.pinggubackend.global.exception.BaseException;
import org.ureca.pinggubackend.global.exception.common.CommonErrorCode;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        List<String> whitelist = List.of("/auth/kakao-login", "/auth/signup", "/auth/token", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**");
        if (whitelist.stream().anyMatch(uri::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        // accessToken 만료되었을 경우
        if (jwtUtil.isExpired(token)) {
            String refreshToken = null;
            if (request.getCookies() != null) {
                for (var cookie : request.getCookies()) {
                    if ("refresh_token".equals(cookie.getName())) {
                        refreshToken = cookie.getValue();
                    }
                }
            }

            if (refreshToken != null && !jwtUtil.isExpired(refreshToken)) {
                Long memberId = jwtUtil.getMemberId(refreshToken);
                String newAccessToken = jwtUtil.createAccessToken(memberId);
                response.setHeader("Authorization", "Bearer " + newAccessToken);

                Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> BaseException.of(CommonErrorCode.USER_NOT_FOUND));
                CustomMemberDetails userDetails = new CustomMemberDetails(member);
                Authentication authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                filterChain.doFilter(request, response);
                return;
            } else {
                response.sendRedirect("/auth/kakao-login");
                return;
            }
        }

        // accessToken 유효할 경우
        Long memberId = jwtUtil.getMemberId(token);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));

        CustomMemberDetails userDetails = new CustomMemberDetails(member);
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
