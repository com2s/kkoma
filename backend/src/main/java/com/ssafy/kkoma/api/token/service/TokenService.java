package com.ssafy.kkoma.api.token.service;

import com.ssafy.kkoma.api.token.dto.AccessTokenResponse;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.global.jwt.constant.GrantType;
import com.ssafy.kkoma.global.jwt.service.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {

    private final MemberService memberService;
    private final TokenManager tokenManager;

    public AccessTokenResponse createAccessTokenByRefreshToken(String refreshToken) {
        Member member = memberService.findMemberByRefreshToken(refreshToken);

        Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime();
        String accessToken = tokenManager.createAccessToken(member.getId(), member.getRole(), accessTokenExpireTime);

        return AccessTokenResponse.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }

}
