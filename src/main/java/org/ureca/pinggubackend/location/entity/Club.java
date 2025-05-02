package org.ureca.pinggubackend.location.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ureca.pinggubackend.global.entity.BaseEntity;
import org.ureca.pinggubackend.recruit.apply.entity.Apply;
import org.ureca.pinggubackend.recruit.entity.Recruit;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Club extends BaseEntity {

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recruit> recruits = new ArrayList<>();

    private String name;

    private String address;

    private String gu;
}
