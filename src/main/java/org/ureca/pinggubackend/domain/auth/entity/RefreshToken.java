package org.ureca.pinggubackend.domain.auth.entity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ureca.pinggubackend.global.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefreshToken extends BaseEntity {

    private Long memberId;

    private String token;

    public void updateToken(String newToken) {
        this.token = newToken;
    }
}
