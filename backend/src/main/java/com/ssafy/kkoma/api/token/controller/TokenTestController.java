package com.ssafy.kkoma.api.token.controller;

import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.domain.member.constant.Role;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.global.jwt.dto.JwtTokenDto;
import com.ssafy.kkoma.global.jwt.service.TokenManager;
import com.ssafy.kkoma.global.util.RandomStringGenerator;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Token")
@Slf4j
@RestController
@RequestMapping("/api/test/token")
@RequiredArgsConstructor
public class TokenTestController {

    private final TokenManager tokenManager;
    private final MemberService memberService;

    @Tag(name = "Token")
    @Operation(summary = "to create a member and a JWT")
    @GetMapping("/create")
    public JwtTokenDto getSampleJwtTokenDto() {
        final String RANDOM_STRING = RandomStringGenerator.randomUUID(12);
        Member member = Member.builder()
                .name(RANDOM_STRING)
                .email(RANDOM_STRING + "@kakao.com")
                .role(Role.USER)
                .memberType(MemberType.KAKAO)
                .build();
        Member registeredMember = memberService.registerMember(member);
        return tokenManager.createJwtTokenDto(registeredMember.getId(), registeredMember.getRole());
    }


    @Tag(name = "Token")
    @Operation(summary = "to create a JWT")
    @GetMapping("/create/{memberId}")
    public JwtTokenDto getJwtTokenDto(@PathVariable("memberId") Long memberId) {
        Member member = memberService.findMemberByMemberId(memberId);
        return tokenManager.createJwtTokenDto(member.getId(), member.getRole());
    }

    @Tag(name = "Token")
    @Operation(summary = "to validate a JWT")
    @GetMapping("/valid")
    public String validJwtToken(@RequestParam("token") String token) {
        tokenManager.validateToken(token);
        Claims claims = tokenManager.getTokenClaims(token);
        Long memberId = Long.valueOf((Integer) claims.get("memberId"));
        String role = (String) claims.get("role");
        log.info("memberId : {}", memberId);
        log.info("role : {}", role);
        return "success";
    }
}
