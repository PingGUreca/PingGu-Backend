package org.ureca.pinggubackend.domain.recruit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitGetDto;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitPostDto;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitPutDto;
import org.ureca.pinggubackend.domain.recruit.service.RecruitService;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recruit")
public class RecruitController {

    private final RecruitService recruitService;

    @PostMapping("")
    public ResponseEntity<Void> postRecruit(@RequestBody RecruitPostDto recruitPostDto) {
        // ToDo: 로그인 개발 완료 되면 유저 정보 가져오기
        Long recruitId = recruitService.postRecruit(recruitPostDto);
        URI uri = URI.create("/recruit/" + recruitId);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("")
    public ResponseEntity<RecruitGetDto> getRecruit(Long recruitId) {
        RecruitGetDto recruitGetDto = recruitService.getRecruit(recruitId);
        return ResponseEntity.ok(recruitGetDto);
    }

    @PutMapping("")
    public ResponseEntity<Void> putRecruit(Long recruitId, @RequestBody RecruitPutDto recruitPutDto) {
        // ToDo: 로그인 개발 완료 되면 유저 정보 가져오기
        recruitService.putRecruit(recruitId, recruitPutDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteMapping(Long recruitId) {
        // ToDo: 로그인 개발 완료 되면 유저 정보 가져오기
        recruitService.deleteRecruit(recruitId);
        return ResponseEntity.ok().build();
    }
}
