package com.ssafy.kkoma.api.token.controller;

import com.ssafy.kkoma.api.login.dto.OauthLoginDto;
import com.ssafy.kkoma.api.login.service.OauthLoginService;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.external.oauth.dto.KakaoTokenDto;
import com.ssafy.kkoma.external.oauth.service.KakaoLoginApiServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Token")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class KakaoTokenController {

    private final KakaoLoginApiServiceImpl kakaoLoginApiService;
    private final OauthLoginService oauthLoginService;

    @Tag(name = "Token")
    @Operation(summary = "to get a kakao token")
    @GetMapping("/oauth/kakao")
    public @ResponseBody OauthLoginDto.Response loginCallback(String code) {
        KakaoTokenDto.Response kakaoToken = kakaoLoginApiService.getKakaoToken(code);
        log.info("kakaoToken={}", kakaoToken.toString());
        return oauthLoginService.oauthLogin(kakaoToken.getAccess_token(), MemberType.KAKAO);
    }

}
