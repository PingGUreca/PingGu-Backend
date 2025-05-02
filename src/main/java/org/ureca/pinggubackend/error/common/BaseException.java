package org.ureca.pinggubackend.error.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException{
    private final HttpStatus status;
    private final String code;
    private final String message;

    protected BaseException(HttpStatus status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public BaseException(ErrorCode errorCode) {
        this(
                errorCode.getStatus(),
                errorCode.getFullCode(),
                errorCode.getMessage()
        );
    }

    public static BaseException of(ErrorCode errorCode) {
        return new BaseException(errorCode);
    }

}
