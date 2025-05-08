package org.ureca.pinggubackend.global.exception.recruit;

import org.ureca.pinggubackend.global.exception.BaseException;
import org.ureca.pinggubackend.global.exception.ErrorCode;

public class RecruitException extends BaseException {

    protected RecruitException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static RecruitException of(RecruitErrorCode recruitErrorCode) {
        return new RecruitException(recruitErrorCode);
    }
}
