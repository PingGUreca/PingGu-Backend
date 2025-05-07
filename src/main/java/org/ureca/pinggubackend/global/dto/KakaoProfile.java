package org.ureca.pinggubackend.global.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoProfile {

    private KakaoAccount kakao_account;

    public void setKakao_acount(KakaoAccount kakao_account) {
        this.kakao_account = kakao_account;
    }

    public static class KakaoAccount {
        private Profile profile;

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }
    }

    public static class Profile {
        private String nickname;
        private String profile_image_url;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getProfile_image_url() {
            return profile_image_url;
        }

        public void setProfile_image_url(String profile_image_url) {
            this.profile_image_url = profile_image_url;
        }
    }
}

