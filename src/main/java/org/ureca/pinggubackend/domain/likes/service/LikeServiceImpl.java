package org.ureca.pinggubackend.domain.likes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ureca.pinggubackend.domain.likes.entity.Likes;
import org.ureca.pinggubackend.domain.likes.repository.LikeRepository;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyLikeResponse;
import org.ureca.pinggubackend.domain.recruit.entity.Recruit;
import org.ureca.pinggubackend.domain.recruit.repository.RecruitRepository;
import org.ureca.pinggubackend.global.exception.BaseException;
import org.ureca.pinggubackend.global.exception.recruit.RecruitException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.ureca.pinggubackend.global.exception.common.CommonErrorCode.USER_NOT_FOUND;
import static org.ureca.pinggubackend.global.exception.recruit.RecruitErrorCode.RECRUIT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final RecruitRepository recruitRepository;

    @Override
    public List<MyLikeResponse> getLikedRecruitList(Long memberId) {
        List<Likes> myLikes = likeRepository.findByMemberId(memberId);

        return myLikes.stream()
                .map(like -> MyLikeResponse.from(like.getRecruit()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean toggleLike(Long recruitId, Long memberId) {
        // 기존 좋아요 확인
        Optional<Likes> existingLike = likeRepository.findByMemberIdAndRecruitId(memberId, recruitId);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            return false;
        } else {
            Member member = memberRepository.findById(memberId).orElseThrow(() -> BaseException.of(USER_NOT_FOUND));
            Recruit recruit = recruitRepository.findById(recruitId).orElseThrow(() -> RecruitException.of(RECRUIT_NOT_FOUND));

            Likes like = new Likes(member, recruit);

            likeRepository.save(like);
            return true;
        }
    }
}
