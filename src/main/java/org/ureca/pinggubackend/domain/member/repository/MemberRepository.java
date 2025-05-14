package org.ureca.pinggubackend.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ureca.pinggubackend.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id); // 로그인 완료시까지 임시로 사용합니다.

    Optional<Member> findByEmail(String email);
}
