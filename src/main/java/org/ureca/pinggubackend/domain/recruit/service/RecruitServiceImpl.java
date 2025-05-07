package org.ureca.pinggubackend.domain.recruit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ureca.pinggubackend.domain.location.entity.Club;
import org.ureca.pinggubackend.domain.location.repository.ClubRepository;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitGetDto;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitPostDto;
import org.ureca.pinggubackend.domain.recruit.dto.request.RecruitPutDto;
import org.ureca.pinggubackend.domain.recruit.dto.response.RecruitResponse;
import org.ureca.pinggubackend.domain.recruit.entity.Recruit;
import org.ureca.pinggubackend.domain.recruit.repository.RecruitRepository;
import org.ureca.pinggubackend.global.exception.BaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.ureca.pinggubackend.global.exception.common.CommonErrorCode.INTERNAL_SERVER_ERROR;
import static org.ureca.pinggubackend.global.exception.common.CommonErrorCode.INVALID_INPUT_VALUE;

@RequiredArgsConstructor
@Service
public class RecruitServiceImpl implements RecruitService {

    private final ClubRepository clubRepository;
    private final RecruitRepository recruitRepository;
    private final MemberRepository memberRepository;

    public Long postRecruit(RecruitPostDto recruitPostDto) {
        Member member = memberRepository.findById(1L).get(); // 로그인 개발전까지 임시로 사용합니다.

        Recruit recruit = mapToRecruit(member, recruitPostDto);
        recruitRepository.save(recruit);
        return recruit.getId();
    }

    public RecruitGetDto getRecruit(Long recruitId) {
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> BaseException.of(INVALID_INPUT_VALUE));
        return mapToRecruitDto(recruit);
    }

    public void putRecruit(Long recruitId, RecruitPutDto recruitPutDto) {
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> BaseException.of(INVALID_INPUT_VALUE));

        // 글을 작성한 유저가 아니라면 예외 발생
        Member member = memberRepository.findById(1L).get(); // 로그인 개발전까지 임시로 사용합니다.
        if (!Objects.equals(recruit.getMember().getId(), member.getId())) {
            throw BaseException.of(INVALID_INPUT_VALUE);
        }

        updateRecruit(recruit, recruitPutDto);
        recruitRepository.save(recruit);
    }

    public void deleteRecruit(Long recruitId) {
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> BaseException.of(INVALID_INPUT_VALUE));

        // 글을 작성한 유저가 아니라면 예외 발생
        Member member = memberRepository.findById(1L).get(); // 로그인 개발전까지 임시로 사용합니다.
        if (!Objects.equals(recruit.getMember().getId(), member.getId())) {
            throw BaseException.of(INVALID_INPUT_VALUE);
        }

        recruitRepository.deleteById(recruitId);
    }

    private Recruit mapToRecruit(Member member, RecruitPostDto recruitPostDto) {
        Club club = clubRepository.findById(recruitPostDto.getClubId())
                .orElseThrow(() -> BaseException.of(INVALID_INPUT_VALUE));

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
                .status(false)
                .build();

        return recruit;
    }

    private RecruitGetDto mapToRecruitDto(Recruit recruit) {
        Club club = clubRepository.findById(recruit.getClub().getId())
                .orElseThrow(() -> BaseException.of(INTERNAL_SERVER_ERROR));

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

    private void updateRecruit(Recruit recruit, RecruitPutDto recruitPutDto) {
        Club club = clubRepository.findById(recruitPutDto.getClubId())
                        .orElseThrow(() -> BaseException.of(INVALID_INPUT_VALUE));

        recruit.setClub(club);
        recruit.setDate(recruitPutDto.getDate());
        recruit.setGender(recruitPutDto.getGender());
        recruit.setLevel(recruit.getLevel());
        recruit.setRacket(recruit.getRacket());
        recruit.setTitle(recruitPutDto.getTitle());
        recruit.setDocument(recruit.getDocument());
        recruit.setChatUrl(recruitPutDto.getChatUrl());

    }

    // 임시!
    public List<RecruitResponse> getRecruitsByMemberId(Long memberId) {
        return new ArrayList<>();
    }

}
