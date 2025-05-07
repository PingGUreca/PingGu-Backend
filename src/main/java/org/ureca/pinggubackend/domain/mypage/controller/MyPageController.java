package org.ureca.pinggubackend.domain.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ureca.pinggubackend.domain.mypage.dto.request.MyPageUpdateRequest;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyPageUpdateResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyPageResponse;
import org.ureca.pinggubackend.domain.mypage.service.MyPageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping
    public ResponseEntity<MyPageResponse> getMyPage(
            @RequestParam long memberId // TODO - JWT 구현 이후 변경 예정
    ){
        return ResponseEntity.ok(myPageService.getMyPage(memberId));
    }

    @PutMapping("/profile")
    public ResponseEntity<MyPageUpdateResponse> editProfile(
            @RequestParam long memberId, // TODO - JWT 구현 이후 변경 예정
            @RequestBody MyPageUpdateRequest request
    ){
        return ResponseEntity.ok(myPageService.editProfile(memberId,request));
    }
}
