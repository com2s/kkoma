package com.ssafy.kkoma.api.token.controller;

import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.global.jwt.dto.JwtTokenDto;
import com.ssafy.kkoma.global.jwt.service.TokenManager;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Token")
@Slf4j
@RestController
@RequestMapping("/api/test/token")
@RequiredArgsConstructor
public class TokenTestController {

    private final TokenManager tokenManager;

    @Tag(name = "Token", description = "to create a JWT")
    @GetMapping("/create")
    public JwtTokenDto getSampleJwtTokenDto() {
        return tokenManager.createJwtTokenDto(1L, Role.ADMIN);
    }

    @Tag(name = "Token", description = "to validate a JWT")
    @GetMapping("/valid")
    public String validJwtToken(@RequestParam String token) {
        tokenManager.validateToken(token);
        Claims claims = tokenManager.getTokenClaims(token);
        Long memberId = Long.valueOf((Integer) claims.get("memberId"));
        String role = (String) claims.get("role");
        log.info("memberId : {}", memberId);
        log.info("role : {}", role);
        return "success";
    }
}
