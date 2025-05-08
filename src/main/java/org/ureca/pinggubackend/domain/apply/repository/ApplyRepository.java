package org.ureca.pinggubackend.domain.apply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ureca.pinggubackend.domain.apply.entity.Apply;

import java.util.Optional;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
    Optional<Apply> findByMemberIdAndRecruitId(Long memberId, Long recruitId);
}
