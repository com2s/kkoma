package com.ssafy.kkoma.api.login.service;

import com.ssafy.kkoma.api.login.dto.OauthLoginDto;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.external.oauth.model.OAuthAttributes;
import com.ssafy.kkoma.external.oauth.service.SocialLoginApiService;
import com.ssafy.kkoma.external.oauth.service.SocialLoginApiServiceFactory;
import com.ssafy.kkoma.global.jwt.dto.JwtTokenDto;
import com.ssafy.kkoma.global.jwt.service.TokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OauthLoginService {

    private final MemberService memberService;
    private final TokenManager tokenManager;

    public OauthLoginDto.Response oauthLogin(String accessToken, MemberType memberType) {
        SocialLoginApiService socialLoginApiService = SocialLoginApiServiceFactory.getSocialLoginApiService(memberType);
        OAuthAttributes userInfo = socialLoginApiService.getUserInfo(accessToken);
        log.info("userInfo : {}",  userInfo);

        JwtTokenDto jwtTokenDto;
        Optional<Member> optionalMember = memberService.findMemberByEmail(userInfo.getEmail());
        if (optionalMember.isEmpty()) { // 신규 회원 가입
            Member oauthMember = userInfo.toMemberEntity(memberType, Role.ADMIN);
            oauthMember = memberService.registerMember(oauthMember);

            jwtTokenDto = tokenManager.createJwtTokenDto(oauthMember.getId(), oauthMember.getRole());
            oauthMember.updateRefreshToken(jwtTokenDto);
        } else { 
            Member oauthMember = optionalMember.get();

            jwtTokenDto = tokenManager.createJwtTokenDto(oauthMember.getId(), oauthMember.getRole());
            oauthMember.updateRefreshToken(jwtTokenDto);
        }

        return OauthLoginDto.Response.fromEntity(jwtTokenDto);
    }

}
