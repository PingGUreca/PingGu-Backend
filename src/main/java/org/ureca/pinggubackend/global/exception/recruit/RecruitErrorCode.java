package org.ureca.pinggubackend.global.exception.recruit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.ureca.pinggubackend.global.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum RecruitErrorCode implements ErrorCode {
    INVALID_CLUB(HttpStatus.BAD_REQUEST, "001", "올바르지 않은 탁구장입니다."),
    RECRUIT_NOT_FOUND(HttpStatus.NOT_FOUND, "002", "존재하지 않는 모집입니다."),
    FORBIDDEN_RECRUIT_ACCESS(HttpStatus.FORBIDDEN, "003", "수정 권한이 없습니다."),
    RECRUIT_ALREADY_FULL(HttpStatus.BAD_REQUEST, "004", "모집 인원이 이미 가득 찼습니다."),
    RECRUIT_NOT_OPEN(HttpStatus.BAD_REQUEST, "005", "모집 중인 상태가 아닙니다."),
    RECRUIT_ALREADY_APPLY(HttpStatus.BAD_REQUEST, "006", "이미 지원한 모집입니다."),
    RECRUIT_CANT_APPLY_CANCEL(HttpStatus.BAD_REQUEST, "007", "이미 마감된 모집은 지원 취소가 불가합니다.");


    private static final String PREFIX = "RECRUIT";
    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public String getFullCode() {
        return PREFIX + "_" + code;
    }
}
