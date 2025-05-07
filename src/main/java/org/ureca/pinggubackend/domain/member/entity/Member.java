package org.ureca.pinggubackend.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ureca.pinggubackend.domain.mypage.dto.request.MyProfileUpdate;
import org.ureca.pinggubackend.global.entity.BaseEntity;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.member.enums.MainHand;
import org.ureca.pinggubackend.domain.member.enums.Racket;
import org.ureca.pinggubackend.domain.apply.entity.Apply;
import org.ureca.pinggubackend.domain.likes.entity.Likes;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member extends BaseEntity {

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apply> applies = new ArrayList<>();

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

}
