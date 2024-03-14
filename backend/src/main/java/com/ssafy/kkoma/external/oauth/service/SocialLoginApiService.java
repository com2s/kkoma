package com.ssafy.kkoma.external.oauth.service;

import com.ssafy.kkoma.external.oauth.model.OAuthAttributes;

public interface SocialLoginApiService {

    OAuthAttributes getUserInfo(String accessToken);

}

