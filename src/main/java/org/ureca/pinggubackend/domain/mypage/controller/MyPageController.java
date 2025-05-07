package org.ureca.pinggubackend.domain.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Get;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.ureca.pinggubackend.domain.apply.service.ApplyService;
import org.ureca.pinggubackend.domain.likes.service.LikeService;
import org.ureca.pinggubackend.domain.member.service.MemberService;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyApplyResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyLikeResponse;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyProfileResponse;
import org.ureca.pinggubackend.domain.mypage.dto.request.MyProfileUpdate;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyRecruitResponse;
import org.ureca.pinggubackend.domain.mypage.service.MyPageService;
import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitResponse;
import org.ureca.pinggubackend.domain.recruit.service.RecruitService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    /**
     * @param Id
     * @return 마이페이지
     */
    @GetMapping("/{Id}")
    public ResponseEntity<MyProfileResponse> getMyPage(@PathVariable Long Id) {
        MyProfileResponse myPageResponse = myPageService.getMyPageInfo(Id);
        return ResponseEntity.ok(myPageResponse);
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


    // TODO: 내 좋아요 취소, 내 신청 취소 -> 해당 게시글 상세 조회 시, 해당 게시글의 좋아요, 신청에 내가 있을 때 삭제 / 없을 때 신청
    // -> RecruitController 에서 하기
}
