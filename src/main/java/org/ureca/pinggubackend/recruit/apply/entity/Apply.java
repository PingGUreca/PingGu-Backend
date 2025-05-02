package org.ureca.pinggubackend.recruit.apply.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ureca.pinggubackend.global.entity.BaseEntity;
import org.ureca.pinggubackend.member.entity.Member;
import org.ureca.pinggubackend.recruit.entity.Recruit;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Apply extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "recruit_id")
    private Recruit recruit;
}
