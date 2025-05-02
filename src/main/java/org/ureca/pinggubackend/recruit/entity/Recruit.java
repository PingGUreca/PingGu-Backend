package org.ureca.pinggubackend.recruit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ureca.pinggubackend.global.entity.BaseEntity;
import org.ureca.pinggubackend.location.entity.Club;
import org.ureca.pinggubackend.member.enums.Gender;
import org.ureca.pinggubackend.member.enums.Level;
import org.ureca.pinggubackend.member.enums.Racket;
import org.ureca.pinggubackend.recruit.apply.entity.Apply;
import org.ureca.pinggubackend.recruit.likes.entity.Likes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Recruit extends BaseEntity {

    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apply> applies = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Integer current;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Racket racket;

    @Column(nullable = false)
    private String title;

    private String document;

    @Column(nullable = false)
    private String chatUrl;

}
