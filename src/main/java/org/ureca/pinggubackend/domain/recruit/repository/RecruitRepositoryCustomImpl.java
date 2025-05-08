package org.ureca.pinggubackend.domain.recruit.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.ureca.pinggubackend.domain.location.entity.QClub;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitPreviewListResponse;
import org.ureca.pinggubackend.domain.recruit.entity.QRecruit;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecruitRepositoryCustomImpl implements RecruitRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<RecruitPreviewListResponse> getRecruitPreviewList(LocalDate date, String gu, Level level, Gender gender) {
        QRecruit recruit = QRecruit.recruit;
        QClub club = QClub.club;

        return queryFactory
                .select(Projections.constructor(
                        RecruitPreviewListResponse.class,
                        club.name,            // clubName
                        recruit.date,
                        recruit.title,
                        recruit.capacity,
                        recruit.current
                ))
                .from(recruit)
                .join(recruit.club, club)
                .where(
                        date != null ? recruit.date.eq(date) : null,
                        gu != null ? club.gu.eq(gu) : null,
                        level != null ? recruit.level.eq(level) : null,
                        gender != null ? recruit.gender.eq(gender) : null
                )
                .fetch();
    }
}
