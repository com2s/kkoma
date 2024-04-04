package com.ssafy.kkoma.api.login.controller;

import com.ssafy.kkoma.api.login.dto.OauthLoginDto;
import com.ssafy.kkoma.api.login.service.OauthLoginService;
import com.ssafy.kkoma.api.login.validator.OauthValidator;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.global.util.ApiUtils;
import com.ssafy.kkoma.global.util.AuthorizationHeaderUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OauthLoginController {

    private final OauthValidator oauthValidator;
    private final OauthLoginService oauthLoginService;

    @Tag(name = "Authentication")
    @Operation(summary = "to sign in")
    @PostMapping("/login")
    public ResponseEntity<ApiUtils.ApiResult<OauthLoginDto.Response>> oauthLogin(@RequestBody OauthLoginDto.Request oauthLoginRequestDto,
                                                             HttpServletRequest httpServletRequest) {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);
        oauthValidator.validateMemberType(oauthLoginRequestDto.getMemberType());

        String accessToken = authorizationHeader.split(" ")[1];
        OauthLoginDto.Response jwtTokenResponseDto = oauthLoginService
                .oauthLogin(accessToken, MemberType.from(oauthLoginRequestDto.getMemberType()));
        return ResponseEntity
                .ok(ApiUtils.success(jwtTokenResponseDto));
    }

}
