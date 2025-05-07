package org.ureca.pinggubackend.domain.likes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ureca.pinggubackend.domain.likes.entity.Likes;
import org.ureca.pinggubackend.domain.likes.repository.LikeRepository;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;
import org.ureca.pinggubackend.domain.mypage.dto.response.MyLikeResponse;
import org.ureca.pinggubackend.global.exception.BaseException;
import org.ureca.pinggubackend.global.exception.common.CommonErrorCode;

import java.util.List;
import java.util.stream.Collectors;

import static org.ureca.pinggubackend.global.exception.common.CommonErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Override
    public List<MyLikeResponse> getLikeListByMemberId(Long memberId) {
        List<Likes> myLikes = likeRepository.findByMemberId(memberId);

        return myLikes.stream()
                .map(like -> MyLikeResponse.from(like.getRecruit()))
                .collect(Collectors.toList());
    }
}
