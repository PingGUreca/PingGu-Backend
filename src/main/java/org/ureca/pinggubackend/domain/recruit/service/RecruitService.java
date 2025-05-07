package org.ureca.pinggubackend.domain.recruit.service;

import org.ureca.pinggubackend.domain.recruit.dto.RecruitGetDto;
import org.ureca.pinggubackend.domain.recruit.dto.RecruitPostDto;

public interface RecruitService {

    Long postRecruit(RecruitPostDto recruitPostDto);

    RecruitGetDto getRecruit(Long recruitId);
}
