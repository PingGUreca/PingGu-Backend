package org.ureca.pinggubackend.domain.recruit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ureca.pinggubackend.domain.apply.service.ApplyService;
import org.ureca.pinggubackend.domain.likes.service.LikeService;
import org.ureca.pinggubackend.domain.location.entity.Club;
import org.ureca.pinggubackend.domain.location.repository.ClubRepository;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyRecruitResponse;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitGetDto;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitPostDto;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitPutDto;
import org.ureca.pinggubackend.domain.recruit.dto.response.ApplyResponse;
import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitIsAuthorDto;
import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitPreviewListResponse;
import org.ureca.pinggubackend.domain.recruit.entity.Recruit;
import org.ureca.pinggubackend.domain.recruit.enums.RecruitStatus;
import org.ureca.pinggubackend.domain.recruit.repository.RecruitRepository;
import org.ureca.pinggubackend.domain.recruit.repository.RecruitRepositoryCustom;
import org.ureca.pinggubackend.global.exception.recruit.RecruitException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.ureca.pinggubackend.global.exception.recruit.RecruitErrorCode.*;

@RequiredArgsConstructor
@Service
public class RecruitServiceImpl implements RecruitService {

    private final ClubRepository clubRepository;
    private final RecruitRepository recruitRepository;
    private final MemberRepository memberRepository;
    private final RecruitRepositoryCustom recruitRepositoryCustom;

    private final LikeService likeService;
    private final ApplyService applyService;

    @Override
    public Long postRecruit(Member member, RecruitPostDto recruitPostDto) {
        Recruit recruit = mapToRecruit(member, recruitPostDto);
        recruitRepository.save(recruit);
        return recruit.getId();
    }

    public RecruitGetDto getRecruit(Long recruitId) {
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> RecruitException.of(RECRUIT_NOT_FOUND));
        return mapToRecruitDto(recruit);
    }

    public RecruitIsAuthorDto isAuthor(Member member, Long recruitId) {
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> RecruitException.of(RECRUIT_NOT_FOUND));
        return new RecruitIsAuthorDto(Objects.equals(member.getId(), recruit.getMember().getId()));
    }

    public void putRecruit(Long memberId, Long recruitId, RecruitPutDto recruitPutDto) {
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> RecruitException.of(RECRUIT_NOT_FOUND));

        if (!Objects.equals(recruit.getMember().getId(), memberId)) {
            throw RecruitException.of(FORBIDDEN_RECRUIT_ACCESS);
        }

        Club club = clubRepository.findById(recruitPutDto.getClubId())
                .orElseThrow(() -> RecruitException.of(INVALID_CLUB));

        recruit.updateRecruit(club, recruitPutDto);
        recruitRepository.save(recruit);
    }

    public void deleteRecruit(Long memberId, Long recruitId) {
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> RecruitException.of(RECRUIT_NOT_FOUND));

        if (!Objects.equals(recruit.getMember().getId(), memberId)) {
            throw RecruitException.of(FORBIDDEN_RECRUIT_ACCESS);
        }

        recruit.closeRecruit();
    }

    @Override
    public Page<RecruitPreviewListResponse> getRecruitPreviewList(
            LocalDate date,
            String gu,
            Level level,
            Gender gender,
            Pageable pageable
    ) {
        return recruitRepositoryCustom.getRecruitPreviewList(date, gu, level, gender, pageable);
    }

    @Override
    public boolean toggleLike(Member member, Long recruitId) {
        return likeService.toggleLike(member, recruitId);
    }

    @Override
    public ApplyResponse cancelApply(Long memberId, Long recruitId) {
        applyService.cancelApply(memberId, recruitId);
        return new ApplyResponse(memberId, recruitId);
    }

    @Override
    public ApplyResponse proceedApply(Member member, Long recruitId) {
        applyService.proceedApply(member, recruitId);
        return new ApplyResponse(member.getId(), recruitId);
    }

    @Override
    public List<MyRecruitResponse> getRecruitListByMemberId(Long memberId) {
        List<Recruit> recruits = recruitRepository.findByMemberIdAndDeleteDateIsNull(memberId);

        return recruits.stream()
                .map(recruit -> MyRecruitResponse.from(recruit))
                .collect(Collectors.toList());
    }

    /**
     * 매일 자정에 실행되는 스케줄 메소드
     * 경기 날짜가 지난 모집 글들의 상태를 EXPIRED로 변경
     */
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    @Transactional
    public void expireOutdatedRecruits() {
        LocalDate today = LocalDate.now();

        // 상태가 OPEN, FULL 이면서 date가 오늘보다 이전인 모집글들 조회
        List<Recruit> outdatedRecruits = recruitRepository.findByStatusInAndDateBefore(
                List.of(RecruitStatus.OPEN, RecruitStatus.FULL), today);

        // 모든 만료된 모집글의 상태 변경
        for (Recruit recruit : outdatedRecruits) {
            recruit.expireRecruit();
        }

        // 변경사항 저장 (JPA dirty checking으로 자동 반영될 수도 있음)
        recruitRepository.saveAll(outdatedRecruits);
    }

    /**
     * 매일 자정에 실행되는 스케줄 메소드
     * 30일 이상 소프트 딜리트된(status가 false이고 deleteDate가 30일 이상 지난) 모집글들을 영구 삭제
     */
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    @Transactional
    public void deleteOldRecruits() {
        // 30일 전 날짜 계산
        LocalDate thirtyDaysAgo = LocalDate.from(LocalDateTime.now().minusDays(30));

        // status가 EXPIRED, CLOSED 이고 deleteDate가 30일 이상 지난 모집글들 조회
        List<Recruit> oldDeletedRecruits = recruitRepository.findByStatusInAndDeleteDateBefore(
                List.of(RecruitStatus.EXPIRED, RecruitStatus.CLOSED),
                thirtyDaysAgo);

        // 영구 삭제
        recruitRepository.deleteAll(oldDeletedRecruits);
    }

    private Recruit mapToRecruit(Member member, RecruitPostDto recruitPostDto) {
        Club club = clubRepository.findById(recruitPostDto.getClubId())
                .orElseThrow(() -> RecruitException.of(INVALID_CLUB));

        Recruit recruit = Recruit.builder()
                .member(member)
                .date(recruitPostDto.getDate())
                .level(recruitPostDto.getLevel())
                .title(recruitPostDto.getTitle())
                .document(recruitPostDto.getDocument())
                .chatUrl(recruitPostDto.getChatUrl())
                .current(0)
                .racket(recruitPostDto.getRacket())
                .capacity(recruitPostDto.getCapacity())
                .gender(recruitPostDto.getGender())
                .club(club)
                .status(RecruitStatus.OPEN)
                .build();

        return recruit;
    }

    private RecruitGetDto mapToRecruitDto(Recruit recruit) {
        Club club = clubRepository.findById(recruit.getClub().getId())
                .orElseThrow(() -> RecruitException.of(INVALID_CLUB));

        RecruitGetDto recruitGetDto = RecruitGetDto.builder()
                .userId(recruit.getMember().getId())
                .userName(recruit.getMember().getName())
                .clubName(club.getName())
                .location(club.getGu())
                .capacity(recruit.getCapacity())
                .level(recruit.getLevel())
                .gender(recruit.getGender())
                .racket(recruit.getRacket())
                .date(recruit.getDate())
                .chatUrl(recruit.getChatUrl())
                .title(recruit.getTitle())
                .document(recruit.getDocument())
                .status(recruit.getStatus())
                .build();

        return recruitGetDto;
    }
}
