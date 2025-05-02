package org.ureca.pinggubackend.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ureca.pinggubackend.global.entity.BaseEntity;
import org.ureca.pinggubackend.member.enums.Gender;
import org.ureca.pinggubackend.member.enums.Level;
import org.ureca.pinggubackend.member.enums.MainHand;
import org.ureca.pinggubackend.member.enums.Racket;
import org.ureca.pinggubackend.recruit.apply.entity.Apply;
import org.ureca.pinggubackend.recruit.likes.entity.Likes;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apply> applies = new ArrayList<>();

    @Column(unique = true)
    private String email;

    private String name;

    private String phoneNumber;

    private String gu;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Enumerated(EnumType.STRING)
    private MainHand mainHand;

    @Enumerated(EnumType.STRING)
    private Racket racket;

}
