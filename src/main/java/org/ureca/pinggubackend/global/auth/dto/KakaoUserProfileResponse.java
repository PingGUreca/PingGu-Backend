package org.ureca.pinggubackend.global.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUserProfileResponse {

    private Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @Setter
    public static class KakaoAccount {
        private Profile profile;
        private String email;

        @JsonProperty("is_email_valid")
        private boolean isEmailValid;

        @JsonProperty("is_email_verified")
        private boolean isEmailVerified;
    }

    @Getter
    @Setter
    public static class Profile {
        private String nickname;

        @JsonProperty("profile_image_url")
        private String profileImageUrl;

        @JsonProperty("thumbnail_image_url")
        private String thumbnailImageUrl;

        @JsonProperty("is_default_image")
        private boolean isDefaultImage;
    }
}
