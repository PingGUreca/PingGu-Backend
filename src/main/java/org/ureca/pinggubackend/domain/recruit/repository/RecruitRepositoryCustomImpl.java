package org.ureca.pinggubackend.domain.recruit.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<RecruitPreviewListResponse> getRecruitPreviewList(
            LocalDate date,
            String gu,
            Level level,
            Gender gender,
            Pageable pageable
    ) {
        QRecruit recruit = QRecruit.recruit;
        QClub club = QClub.club;

        BooleanBuilder builder = new BooleanBuilder();
        if (date != null) builder.and(recruit.date.eq(date));
        if (gu != null) builder.and(club.gu.eq(gu));
        if (level != null) builder.and(recruit.level.eq(level));
        if (gender != null) builder.and(recruit.gender.eq(gender));

        List<RecruitPreviewListResponse> content = queryFactory
                .select(Projections.constructor(
                        RecruitPreviewListResponse.class,
                        recruit.id,
                        club.name,
                        recruit.date,
                        recruit.title,
                        recruit.capacity,
                        recruit.current,
                        recruit.status
                ))
                .from(recruit)
                .join(recruit.club, club)
                .where(builder)
                .orderBy(recruit.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(recruit.count())
                .from(recruit)
                .join(recruit.club, club)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
