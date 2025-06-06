package org.ureca.pinggubackend.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ureca.pinggubackend.domain.apply.entity.Apply;
import org.ureca.pinggubackend.domain.likes.entity.Likes;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.member.enums.MainHand;
import org.ureca.pinggubackend.domain.member.enums.Racket;
import org.ureca.pinggubackend.domain.recruit.entity.Recruit;
import org.ureca.pinggubackend.global.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member extends BaseEntity {

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apply> applies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recruit> recruitList = new ArrayList<>();

    @Column(unique = true, nullable = false)
    private Long kakaoId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String gu;

//    @Column(nullable = false)
    private String profileImgUrl;

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

    public void updateProfileImg(String url) {
        this.profileImgUrl = url;
    }
}