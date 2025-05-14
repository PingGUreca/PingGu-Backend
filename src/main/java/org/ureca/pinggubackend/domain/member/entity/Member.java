package org.ureca.pinggubackend.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ureca.pinggubackend.domain.member.enums.*;
import org.ureca.pinggubackend.domain.recruit.entity.Recruit;
import org.ureca.pinggubackend.global.entity.BaseEntity;
import org.ureca.pinggubackend.domain.apply.entity.Apply;
import org.ureca.pinggubackend.domain.likes.entity.Likes;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Member extends BaseEntity {

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apply> applies = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recruit> recruitList = new ArrayList<>();

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String gu;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MainHand mainHand;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Racket racket;

    public void updateProfile(
            String name,
            Gender gender,
            String gu,
            Level level,
            MainHand mainHand,
            Racket racket
    ) {
        this.name = name;
        this.gender = gender;
        this.gu = gu;
        this.level = level;
        this.mainHand = mainHand;
        this.racket = racket;
    }
}