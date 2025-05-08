package org.ureca.pinggubackend.domain.recruit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.recruit.entity.Recruit;

import java.util.List;
import java.util.Optional;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {

    List<Recruit> findByMemberId(Long memberId);
}
