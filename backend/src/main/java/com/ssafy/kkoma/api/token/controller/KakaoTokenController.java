package com.ssafy.kkoma.api.token.controller;

import com.ssafy.kkoma.api.login.dto.OauthLoginDto;
import com.ssafy.kkoma.api.login.service.OauthLoginService;
import com.ssafy.kkoma.api.token.service.KakaoTokenService;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.external.oauth.dto.KakaoTokenDto;
import com.ssafy.kkoma.external.oauth.model.OAuthAttributes;
import com.ssafy.kkoma.external.oauth.service.KakaoLoginApiServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Token")
@Controller
@RequiredArgsConstructor
public class KakaoTokenController {

    private final KakaoLoginApiServiceImpl kakaoLoginApiService;
    private final OauthLoginService oauthLoginService;

    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @Tag(name = "Token")
    @Operation(summary = "to get a kakao token")
    @GetMapping("/oauth/kakao/callback")
    public @ResponseBody OauthLoginDto.Response loginCallback(String code) {
        KakaoTokenDto.Response kakaoToken = kakaoLoginApiService.getKakaoToken(code);
        return oauthLoginService.oauthLogin(kakaoToken.getAccess_token(), MemberType.KAKAO);
    }

}
