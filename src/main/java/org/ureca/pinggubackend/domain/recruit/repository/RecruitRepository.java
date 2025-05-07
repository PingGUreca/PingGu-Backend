package org.ureca.pinggubackend.domain.recruit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ureca.pinggubackend.domain.recruit.entity.Recruit;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {

}
