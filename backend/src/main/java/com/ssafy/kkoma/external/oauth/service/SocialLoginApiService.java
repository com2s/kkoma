package com.ssafy.kkoma.external.oauth.service;

import com.ssafy.kkoma.external.oauth.dto.KakaoTokenDto;
import com.ssafy.kkoma.external.oauth.model.OAuthAttributes;

public interface SocialLoginApiService {

    KakaoTokenDto.Response getKakaoToken(String code);

    OAuthAttributes getUserInfo(String accessToken);

}

