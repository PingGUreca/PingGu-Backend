package org.ureca.pinggubackend.domain.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.ureca.pinggubackend.domain.member.dto.CustomMemberDetails;
import org.ureca.pinggubackend.domain.mypage.dto.request.MyPageUpdateRequest;
import org.ureca.pinggubackend.domain.mypage.dto.response.*;
import org.ureca.pinggubackend.domain.mypage.service.MyPageService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping
    public ResponseEntity<MyPageResponse> getMyPage(
            @AuthenticationPrincipal CustomMemberDetails member
            ){
        return ResponseEntity.ok(myPageService.getMyPage(member.getMember()));
    }

    @PutMapping("/profile")
    public ResponseEntity<MyPageUpdateResponse> editProfile(
            @AuthenticationPrincipal CustomMemberDetails principal,
            @RequestBody MyPageUpdateRequest request
    ){
        return ResponseEntity.ok(myPageService.editProfile(principal.getMember(), request));
    }

    @PostMapping(value = "/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> changeProfileImg(
            @AuthenticationPrincipal CustomMemberDetails principal,
            @RequestParam MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(myPageService.changeProfileImg(principal.getMember(), file));
    }

    @DeleteMapping
    public ResponseEntity<MyPageDeleteResponse> deleteMember(
            @AuthenticationPrincipal CustomMemberDetails principal
    ){
        return ResponseEntity.ok(myPageService.deleteMember(principal.getMember()));
    }

    @GetMapping("/applies")
    public ResponseEntity<List<MyApplyResponse>> getMyApplies(@AuthenticationPrincipal CustomMemberDetails principal) {
        List<MyApplyResponse> response = myPageService.getMyApplies(principal.getMember().getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/likes")
    public ResponseEntity<List<MyLikeResponse>> getMyLikes(@AuthenticationPrincipal CustomMemberDetails principal) {
        List<MyLikeResponse> response = myPageService.getMyLikes(principal.getMember().getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recruits")
    public ResponseEntity<List<MyRecruitResponse>> getMyRecruits(@AuthenticationPrincipal CustomMemberDetails principal) {
        List<MyRecruitResponse> response = myPageService.getMyRecruits(principal.getMember().getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/applies")
    public ResponseEntity<MyPageCancelResponse> cancelApply(
            @AuthenticationPrincipal CustomMemberDetails principal,
            @RequestParam Long recruitId
    ){
        return ResponseEntity.ok(myPageService.cancelApply(principal.getMember().getId(), recruitId));
    }
}