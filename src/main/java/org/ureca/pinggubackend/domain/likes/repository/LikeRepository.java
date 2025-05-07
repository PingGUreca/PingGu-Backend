package org.ureca.pinggubackend.domain.likes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.ureca.pinggubackend.domain.likes.entity.Likes;
import org.ureca.pinggubackend.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByMemberIdAndRecruitId(Long memberId, Long recruitId);

    int countByRecruitId(Long recruitId);

    int countByMemberId(Long memberId);

    List<Likes> findByMemberId(Long memberId);
}
