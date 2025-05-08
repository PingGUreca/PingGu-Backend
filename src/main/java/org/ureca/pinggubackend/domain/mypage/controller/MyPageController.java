package org.ureca.pinggubackend.domain.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Get;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ureca.pinggubackend.domain.mypage.dto.request.MyPageUpdateRequest;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyPageCancelResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyPageDeleteResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyPageUpdateResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyPageResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyApplyResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyLikeResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyProfileResponse;
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
  
    @DeleteMapping
    public ResponseEntity<MyPageDeleteResponse> deleteMember(
            @RequestParam long memberId // TODO - JWT 구현 이후 변경 예정
    ){
        return ResponseEntity.ok(myPageService.deleteMember(memberId));
    }

    @GetMapping("/applies")
    public ResponseEntity<List<MyApplyResponse>> getMyApplies(@RequestParam Long memberId) {
        List<MyApplyResponse> response = myPageService.getMyApplies(memberId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/likes")
    public ResponseEntity<List<MyLikeResponse>> getMyLikes(@RequestParam Long memberId) {
        List<MyLikeResponse> response = myPageService.getMyLikes(memberId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recruits")
    public ResponseEntity<List<MyRecruitResponse>> getMyRecruits(@RequestParam Long memberId) {
        List<MyRecruitResponse> response = myPageService.getMyRecruits(memberId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/applies")
    public ResponseEntity<MyPageCancelResponse> cancelApply(
            @RequestParam long memberId, // TODO - JWT 구현 이후 변경 예정
            @RequestParam long recruitId
    ){
        return ResponseEntity.ok(myPageService.cancelApply(memberId,recruitId));
    }
}