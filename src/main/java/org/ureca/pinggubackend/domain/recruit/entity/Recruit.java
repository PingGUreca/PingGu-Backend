package org.ureca.pinggubackend.domain.recruit.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitPutDto;
import org.ureca.pinggubackend.global.entity.BaseEntity;
import org.ureca.pinggubackend.domain.location.entity.Club;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.member.enums.Racket;
import org.ureca.pinggubackend.domain.apply.entity.Apply;
import org.ureca.pinggubackend.domain.likes.entity.Likes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Recruit extends BaseEntity {

    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Apply> applies = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

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

    @Column(nullable = false)
    private Boolean status;

    public void updateRecruit(Club club, RecruitPutDto dto) {
        this.club = club;
        this.date = dto.getDate();
        this.gender = dto.getGender();
        this.level = dto.getLevel();
        this.racket = dto.getRacket();
        this.title = dto.getTitle();
        this.document = dto.getDocument();
        this.chatUrl = dto.getChatUrl();
    }

}
