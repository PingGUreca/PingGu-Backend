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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruitId;

    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apply> applies = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    private LocalDate date;

    private Integer capacity;

    private Integer current;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Enumerated(EnumType.STRING)
    private Racket racket;

    private String title;

    private String document;

    private String chatUrl;

}
