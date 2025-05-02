package org.ureca.pinggubackend.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getStatus();

    String getCode();

    String getMessage();

    String getFullCode();
}