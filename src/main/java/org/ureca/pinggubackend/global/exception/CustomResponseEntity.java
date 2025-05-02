package org.ureca.pinggubackend.global.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class CustomResponseEntity {
    private int status;
    private String code;
    private String message;

    public static ResponseEntity<CustomResponseEntity> toResponseEntity(BaseException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(CustomResponseEntity.builder()
                        .status(e.getStatus().value())
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build());
    }
}
