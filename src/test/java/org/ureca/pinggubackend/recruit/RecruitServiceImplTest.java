package org.ureca.pinggubackend.recruit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.ureca.pinggubackend.domain.location.entity.Club;
import org.ureca.pinggubackend.domain.location.repository.ClubRepository;
import org.ureca.pinggubackend.domain.member.entity.Member;
import org.ureca.pinggubackend.domain.member.enums.Gender;
import org.ureca.pinggubackend.domain.member.enums.Level;
import org.ureca.pinggubackend.domain.member.enums.Racket;
import org.ureca.pinggubackend.domain.member.repository.MemberRepository;
import org.ureca.pinggubackend.domain.recruit.entity.Recruit;
import org.ureca.pinggubackend.domain.recruit.enums.RecruitStatus;
import org.ureca.pinggubackend.domain.recruit.repository.RecruitRepository;
import org.ureca.pinggubackend.domain.recruit.service.RecruitServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RecruitServiceImplTest {

    @Autowired
    private RecruitServiceImpl recruitService;

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ClubRepository clubRepository;

    private Member testMember;
    private Club testClub;
    private List<Long> createdRecruitIds = new ArrayList<>();

    @BeforeEach
    void setUp() {
        // 테스트에 필요한 기본 데이터 생성
        testMember = memberRepository.findById(100L).orElseThrow();
        testClub = clubRepository.findById(10L).orElseThrow();
    }

    @AfterEach
    void tearDown() {
        // 테스트 중 생성된 모든 모집글 삭제
        recruitRepository.deleteAllById(createdRecruitIds);
        createdRecruitIds.clear();
    }

    @Test
    @DisplayName("만료된 모집글 상태 변경 테스트")
    void expireOutdatedRecruits() {
        // 1. 테스트 데이터 준비: 과거 날짜의 모집글 생성
        Recruit outdatedRecruit = createTestRecruit(LocalDate.now().minusDays(1), RecruitStatus.OPEN);
        Recruit futureRecruit = createTestRecruit(LocalDate.now().plusDays(1), RecruitStatus.OPEN);

        // 2. 스케줄링 메서드 실행
        recruitService.expireOutdatedRecruits();

        // 3. 결과 확인
        Recruit updatedOutdatedRecruit = recruitRepository.findById(outdatedRecruit.getId()).orElseThrow();
        Recruit updatedFutureRecruit = recruitRepository.findById(futureRecruit.getId()).orElseThrow();

        // 4. 검증
        assertEquals(RecruitStatus.EXPIRED, updatedOutdatedRecruit.getStatus(),
                "과거 날짜 모집글은 EXPIRED 상태로 변경되어야 함");
        assertNotNull(updatedOutdatedRecruit.getDeleteDate(),
                "과거 날짜 모집글은 deleteDate가 설정되어야 함");

        assertEquals(RecruitStatus.OPEN, updatedFutureRecruit.getStatus(),
                "미래 날짜 모집글은 상태가 변경되지 않아야 함");
        assertNull(updatedFutureRecruit.getDeleteDate(),
                "미래 날짜 모집글은 deleteDate가 설정되지 않아야 함");
    }

    @Test
    @DisplayName("오래된 모집글 삭제 테스트")
    void deleteOldRecruits() {
        // 1. 테스트 데이터 준비
        // 31일 전에 만료된 모집글 (삭제 대상)
        Recruit oldExpiredRecruit = createAndExpireRecruit(LocalDate.now().minusDays(31));

        // 20일 전에 만료된 모집글 (삭제 대상 아님)
        Recruit recentExpiredRecruit = createAndExpireRecruit(LocalDate.now().minusDays(20));

        // 2. 스케줄링 메서드 실행
        recruitService.deleteOldRecruits();

        // 3. 결과 확인
        boolean oldRecruitExists = recruitRepository.existsById(oldExpiredRecruit.getId());
        boolean recentRecruitExists = recruitRepository.existsById(recentExpiredRecruit.getId());

        // 4. 검증
        assertFalse(oldRecruitExists, "30일 이상 지난 모집글은 삭제되어야 함");
        assertTrue(recentRecruitExists, "30일 미만 지난 모집글은 삭제되지 않아야 함");
    }

    @Test
    @DisplayName("만료 처리 성능 측정")
    void measureExpirePerformance() {
        // 1. 대량의 테스트 데이터 생성
        createBulkTestData(1000); // 테스트 규모에 맞게 조정

        // 2. 성능 측정 시작
        long startTime = System.currentTimeMillis();

        // 3. 스케줄링 메서드 실행
        recruitService.expireOutdatedRecruits();

        // 4. 성능 측정 종료
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        // 5. 결과 출력
        System.out.println("만료 처리 실행 시간: " + executionTime + "ms");
        System.out.println("영향 받은 행 수: " +
                recruitRepository.countByStatus(RecruitStatus.EXPIRED));
    }

    // 테스트용 헬퍼 메서드들
    private Recruit createTestRecruit(LocalDate date, RecruitStatus status) {
        Recruit recruit = Recruit.builder()
                .member(testMember)
                .club(testClub)
                .date(date)
                .status(status)
                .capacity(4)
                .current(0)
                .gender(Gender.ALL)
                .level(Level.BEGINNER)
                .racket(Racket.PEN_HOLDER)
                .title("테스트 모집글 " + UUID.randomUUID())
                .document("테스트 내용")
                .chatUrl("https://chat.url")
                .build();

        Recruit savedRecruit = recruitRepository.save(recruit);
        createdRecruitIds.add(savedRecruit.getId()); // 나중에 정리할 수 있도록 ID 저장
        return savedRecruit;
    }

    private Recruit createAndExpireRecruit(LocalDate deleteDate) {
        // 1. 기본 모집글 생성
        Recruit recruit = createTestRecruit(LocalDate.now().minusDays(40), RecruitStatus.OPEN);

        // 2. 상태를 EXPIRED로 변경하고 deleteDate 설정
        recruit.expireRecruit(); // 이 메서드가 status를 EXPIRED로 설정하고 현재 시간으로 deleteDate 설정

        // 3. deleteDate를 테스트용으로 지정된 날짜로 변경 (리플렉션 사용)
        // 참고: 실제 테스트에서는 JPA Repository에 맞는 방식으로 조정 필요
        ReflectionTestUtils.setField(recruit, "deleteDate", deleteDate.atStartOfDay().toLocalDate());

        return recruitRepository.save(recruit);
    }

    private void createBulkTestData(int count) {
        List<Recruit> recruits = new ArrayList<>();

        // 과거 날짜의 OPEN 상태 모집글 생성
        for (int i = 0; i < count; i++) {
            Recruit recruit = Recruit.builder()
                    .member(testMember)
                    .club(testClub)
                    .date(LocalDate.now().minusDays(i % 30 + 1)) // 1~30일 전 날짜
                    .status(RecruitStatus.OPEN)
                    .capacity(4)
                    .current(0)
                    .gender(Gender.ALL)
                    .level(Level.BEGINNER)
                    .racket(Racket.PEN_HOLDER)
                    .title("테스트 모집글 " + i)
                    .document("테스트 내용")
                    .chatUrl("https://chat.url")
                    .build();

            recruits.add(recruit);
        }

        List<Recruit> savedRecruits = recruitRepository.saveAll(recruits);

        // 생성된 모든 모집글의 ID 저장 (나중에 정리하기 위함)
        savedRecruits.forEach(recruit -> createdRecruitIds.add(recruit.getId()));
    }
}