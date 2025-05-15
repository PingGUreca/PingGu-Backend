package org.ureca.pinggubackend.domain.recruit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.ureca.pinggubackend.domain.member.dto.CustomMemberDetails;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitGetDto;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitPostDto;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitPutDto;
import org.ureca.pinggubackend.domain.recruit.dto.response.ApplyResponse;
import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitIsAuthorDto;
import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitPreviewListResponse;
import org.ureca.pinggubackend.domain.recruit.service.RecruitService;

import java.net.URI;
import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recruit")
public class RecruitController {

    private final RecruitService recruitService;

    @GetMapping
    public ResponseEntity<Page<RecruitPreviewListResponse>> getFilteredRecruits(
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) String gu,
            @RequestParam(required = false) Level level,
            @RequestParam(required = false) Gender gender,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<RecruitPreviewListResponse> result = recruitService.getRecruitPreviewList(date, gu, level, gender, pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<Void> postRecruit(@AuthenticationPrincipal CustomMemberDetails principal,
                                            @RequestBody @Valid RecruitPostDto recruitPostDto) {
        Long recruitId = recruitService.postRecruit(principal.getMember(), recruitPostDto);
        URI uri = URI.create("/recruit/" + recruitId);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{recruitId}")
    public ResponseEntity<RecruitGetDto> getRecruit(@PathVariable Long recruitId) {
        RecruitGetDto recruitGetDto = recruitService.getRecruit(recruitId);
        return ResponseEntity.ok(recruitGetDto);
    }

    @GetMapping("/author/{recruitId}")
    public ResponseEntity<RecruitIsAuthorDto> isAuthor(@AuthenticationPrincipal CustomMemberDetails principal,
                                     @PathVariable Long recruitId) {
        RecruitIsAuthorDto recruitIsAuthorDto = recruitService.isAuthor(principal.getMember(), recruitId);
        return ResponseEntity.ok(recruitIsAuthorDto);
    }

    @PutMapping("/{recruitId}")
    public ResponseEntity<Void> putRecruit(@PathVariable Long recruitId,
                                           @AuthenticationPrincipal CustomMemberDetails principal,
                                           @RequestBody @Valid RecruitPutDto recruitPutDto) {
        recruitService.putRecruit(principal.getMember().getId(), recruitId, recruitPutDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{recruitId}")
    public ResponseEntity<Void> deleteMapping(@AuthenticationPrincipal CustomMemberDetails principal,
                                              @PathVariable Long recruitId) {
        recruitService.deleteRecruit(principal.getMember().getId(), recruitId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{recruitId}/like")
    public ResponseEntity<Boolean> toggleLike(@AuthenticationPrincipal CustomMemberDetails principal, @PathVariable Long recruitId) {
        boolean isLiked = recruitService.toggleLike(principal.getMember(), recruitId);
        return ResponseEntity.ok(isLiked);
    }

    @PostMapping("/{recruitId}/apply")
    public ResponseEntity<ApplyResponse> proceedApply(@AuthenticationPrincipal CustomMemberDetails principal, @PathVariable Long recruitId) {
        return ResponseEntity.ok(recruitService.proceedApply(principal.getMember(), recruitId));
    }

    @DeleteMapping("/{recruitId}/apply")
    public ResponseEntity<ApplyResponse> cancelApply(@AuthenticationPrincipal CustomMemberDetails principal,
                                                     @PathVariable Long recruitId
    ) {
        return ResponseEntity.ok(recruitService.cancelApply(principal.getMember().getId(), recruitId));
    }
}
