package org.ureca.pinggubackend.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_LOG_FORMAT = "[{}] {} :: {}";

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<CustomResponseEntity> handleBaseException(BaseException e, HttpServletRequest request) {
//        log.error(ERROR_LOG_FORMAT, e.getStatus(), request.getRequestURI(), e.getMessage());
        return CustomResponseEntity.toResponseEntity(e);
    }
}
