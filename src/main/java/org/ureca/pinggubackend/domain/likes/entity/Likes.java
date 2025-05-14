package org.ureca.pinggubackend.domain.likes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.recruit.entity.Recruit;
import org.ureca.pinggubackend.global.entity.BaseEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "recruit_id"})
})
public class Likes extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;
}
