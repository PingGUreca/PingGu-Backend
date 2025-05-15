package org.ureca.pinggubackend.domain.auth.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.ureca.pinggubackend.domain.auth.dto.KakaoUserProfileResponse;

@Component
@RequiredArgsConstructor
public class KakaoProfileApi {

    private final RestTemplate restTemplate;

    public KakaoUserProfileResponse getUserProfile(String kakaoAccessToken) {
        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(kakaoAccessToken);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<KakaoUserProfileResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                KakaoUserProfileResponse.class
        );

        return response.getBody();
    }
}