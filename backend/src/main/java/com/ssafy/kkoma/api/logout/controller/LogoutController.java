package com.ssafy.kkoma.api.logout.controller;

import com.ssafy.kkoma.api.logout.service.LogoutService;
import com.ssafy.kkoma.global.util.ApiUtils;
import com.ssafy.kkoma.global.util.AuthorizationHeaderUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LogoutController {

    private final LogoutService logoutService;

    @Tag(name = "Authentication")
    @Operation(summary = "to sign out", security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/logout")
    public ResponseEntity<ApiUtils.ApiResult<String>> logout(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);

        String accessToken = authorizationHeader.split(" ")[1];
        logoutService.logout(accessToken);

        return ResponseEntity.ok(ApiUtils.success("logout success"));
    }

}
