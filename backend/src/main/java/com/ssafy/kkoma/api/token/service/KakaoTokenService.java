package com.ssafy.kkoma.api.token.service;

import com.ssafy.kkoma.external.oauth.client.KakaoTokenClient;
import com.ssafy.kkoma.external.oauth.dto.KakaoTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoTokenService {

    private final KakaoTokenClient kakaoTokenClient;

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    @Value("${kkoma.domain")
    private String serverDomain;

    @Value("${server.port}")
    private String serverPort;

    public KakaoTokenDto.Response getKakaoToken(String code) {
        String contentType = "application/x-www-form-urlencoded;charset=utf-8";
        KakaoTokenDto.Request kakaoTokenRequestDto = KakaoTokenDto.Request.builder()
                .client_id(clientId)
                .client_secret(clientSecret)
                .grant_type("authorization_code")
                .code(code)
                .redirect_uri("http://" + serverDomain + ":" + serverPort + "/oauth/kakao/callback")
                .build();
        KakaoTokenDto.Response kakaoToken = kakaoTokenClient.requestKakaoToken(contentType, kakaoTokenRequestDto);
        return kakaoToken;
    }

}
