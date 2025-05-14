package org.ureca.pinggubackend.global.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ureca.pinggubackend.global.auth.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    // 이메일로 조회하기
    Optional<RefreshToken> findByEmail(String token);

    // 토큰으로 조회화기
    Optional<RefreshToken> findByToken(String token);

    // 로그아웃시 refreshToken 삭제
    //void deleteByToken(String token);
}
