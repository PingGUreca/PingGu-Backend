package org.ureca.pinggubackend.domain.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.ureca.pinggubackend.domain.apply.service.ApplyService;
import org.ureca.pinggubackend.domain.likes.service.LikeService;
import org.ureca.pinggubackend.domain.member.service.MemberService;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyProfileResponse;
import org.ureca.pinggubackend.domain.mypage.dto.request.MyProfileUpdate;
import org.ureca.pinggubackend.domain.mypage.service.MyPageService;
import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitResponse;
import org.ureca.pinggubackend.domain.recruit.service.RecruitService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/me")
public class MyPageController {

//    private final RecruitService recruitService;
//    private final ApplyService applyService;
//    private final LikeService likeService;
//    private final MemberService memberService;
    private final MyPageService myPageService;

    /**
     * @param Id
     * @return 마이페이지
     */
    @GetMapping("/{Id}")
    public ResponseEntity<?> getMyPage(@PathVariable Long Id) {
        MyProfileResponse myPageResponse = myPageService.getMyPageInfo(Id);
        return ResponseEntity.ok(myPageResponse);
    }

//    /**
//     * @param memberId
//     * @return 내가 올린 모집(들)
//     */
//    @GetMapping("/my-recruit")
//    public ResponseEntity<List<RecruitResponse>> getMyRecruits(@AuthenticationPrincipal Long memberId) {
//        List<RecruitResponse> myRecruits = recruitService.getRecruitsByMemberId(memberId);
//        return ResponseEntity.ok(myRecruits);
//    }
//
//    /**
//     * @param memberId
//     * @return 내가 지원한 모집(들)
//     */
//    @GetMapping("/apply-list")
//    public ResponseEntity<List<RecruitResponse>> getMyLikes(@AuthenticationPrincipal Long memberId) {
//        List<RecruitResponse> myApplies = applyService.getLikesByMemberId(memberId);
//        return ResponseEntity.ok(myApplies);
//    }
//
//    /**
//     * @param memberId
//     * @return 내가 좋아요한 모집(들)
//     */
//    @GetMapping("/likes")
//    public ResponseEntity<List<RecruitResponse>> getMyLikes(@AuthenticationPrincipal Long memberId) {
//        List<RecruitResponse> myLikes = likeService.getLikesByMemberId(memberId);
//        return ResponseEntity.ok(myLikes);
//    }
//
//    /**
//     * @param memberId
//     * @param request
//     * @return 내 프로필 수정
//     */
//    @PutMapping("/profile")
//    public ResponseEntity<MyProfileResponse> updateProfile(@AuthenticationPrincipal Long memberId, @RequestBody MyProfileUpdate request) {
//        MyProfileResponse memberProfile = memberService.updateMyProfile(memberId, request);
//        return ResponseEntity.ok(memberProfile);
//    }

    // TODO: 내 좋아요 취소, 내 신청 취소 -> 해당 게시글 상세 조회 시, 해당 게시글의 좋아요, 신청에 내가 있을 때 삭제 / 없을 때 신청
    // -> RecruitController 에서 하기
}
