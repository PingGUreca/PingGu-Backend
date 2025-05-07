package org.ureca.pinggubackend.domain.location.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ureca.pinggubackend.domain.location.dto.LocationGetDto;
import org.ureca.pinggubackend.domain.location.entity.Club;
import org.ureca.pinggubackend.domain.location.repository.ClubRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final ClubRepository clubRepository;

    public LocationGetDto getLocations(String gu) {
        List<Club> clubs = clubRepository.findByGu(gu);
        return new LocationGetDto(clubs);
    }
}
