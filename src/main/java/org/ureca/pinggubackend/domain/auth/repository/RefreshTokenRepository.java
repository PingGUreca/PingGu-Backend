package org.ureca.pinggubackend.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ureca.pinggubackend.domain.auth.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberId(Long memberId);

    Optional<RefreshToken> findByToken(String token);
    // 로그아웃시 refreshToken 삭제
    //void deleteByToken(String token);
}
