package org.ureca.pinggubackend.domain.recruit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ureca.pinggubackend.domain.recruit.entity.Recruit;
import org.ureca.pinggubackend.domain.recruit.enums.RecruitStatus;

import java.time.LocalDate;
import java.util.List;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {
    List<Recruit> findByMemberId(Long memberId);

    /**
     * SELECT r.*
     * FROM recruit r
     * WHERE r.status IN ('OPEN', 'FULL')
     *      AND r.date < today
     */
    List<Recruit> findByStatusInAndDateBefore(List<RecruitStatus> status, LocalDate dateTime);
    /**
     * SELECT r.*
     FROM recruit r
     WHERE r.status IN ('EXPIRED', 'CLOSED', 'FULL')
     AND r.delete_date < '2025-04-13 00:00:00'
     * */
    List<Recruit> findByStatusInAndDeleteDateBefore(List<RecruitStatus> status, LocalDate thirtyDaysAgo);

    Integer countByStatus(RecruitStatus recruitStatus);

    List<Recruit> findByMemberIdAndDeleteDateIsNull(Long memberId);
}
