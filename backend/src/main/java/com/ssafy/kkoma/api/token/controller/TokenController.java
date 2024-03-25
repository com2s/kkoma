package com.ssafy.kkoma.api.token.controller;

import com.ssafy.kkoma.api.token.dto.AccessTokenResponse;
import com.ssafy.kkoma.api.token.service.TokenService;
import com.ssafy.kkoma.global.util.ApiUtils;
import com.ssafy.kkoma.global.util.AuthorizationHeaderUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Token")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TokenController {

    private final TokenService tokenService;

    @Tag(name = "Token")
    @Operation(summary = "to issue an access token")
    @PostMapping("/access-token/issue")
    public ResponseEntity<ApiUtils.ApiResult<AccessTokenResponse>> createAccessToken(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);

        String refreshToken = authorizationHeader.split(" ")[1];
        AccessTokenResponse accessTokenResponse = tokenService.createAccessTokenByRefreshToken(refreshToken);
        return ResponseEntity
                .ok(ApiUtils.success(accessTokenResponse));
    }

}
