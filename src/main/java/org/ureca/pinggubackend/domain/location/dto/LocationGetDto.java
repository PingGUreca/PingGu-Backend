package org.ureca.pinggubackend.domain.location.dto;

import lombok.Getter;
import org.ureca.pinggubackend.domain.location.entity.Club;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LocationGetDto {

    private final List<ClubDto> clubs;

    public LocationGetDto(List<Club> list) {
        List<ClubDto> clubDtos = new ArrayList<>();
        for (Club club : list) {
            clubDtos.add(new ClubDto(club.getId(), club.getName()));
        }
        this.clubs = clubDtos;
    }
}
