package org.ureca.pinggubackend.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ureca.pinggubackend.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
