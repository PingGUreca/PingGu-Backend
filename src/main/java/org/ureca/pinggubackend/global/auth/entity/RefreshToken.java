package org.ureca.pinggubackend.global.auth.entity;

import jakarta.persistence.Entity;
import lombok.*;
import org.ureca.pinggubackend.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefreshToken extends BaseEntity {

    private String email;

    private String token;

    public void updateToken(String newToken) {
        this.token = newToken;
    }

}
