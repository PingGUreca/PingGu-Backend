package org.ureca.pinggubackend.error.common;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getStatus();

    String getCode();

    String getMessage();

    String getFullCode();
}