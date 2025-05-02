package org.ureca.pinggubackend.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.ureca.pinggubackend.error.common.ApiResponse;
import org.ureca.pinggubackend.error.common.BaseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_LOG_FORMAT = "[{}] {} :: {}";

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException e, HttpServletRequest request) {
//        log.error(ERROR_LOG_FORMAT, e.getStatus(), request.getRequestURI(), e.getMessage());
        return ResponseEntity.status(e.getStatus())
                .body(ApiResponse.fail(e));
    }
}
