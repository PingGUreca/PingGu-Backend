package org.ureca.pinggubackend.domain.location.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ureca.pinggubackend.domain.location.dto.LocationGetDto;
import org.ureca.pinggubackend.domain.location.service.LocationService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    @GetMapping("")
    public ResponseEntity<LocationGetDto> getLocation(String gu) {
        LocationGetDto locationGetDto = locationService.getLocations(gu);
        return ResponseEntity.ok(locationGetDto);
    }
}
