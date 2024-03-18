package com.ssafy.kkoma.api.member.controller;

import com.ssafy.kkoma.api.member.dto.response.MemberInfoResponse;
import com.ssafy.kkoma.api.member.dto.request.UpdateMemberRequest;
import com.ssafy.kkoma.api.member.service.MemberInfoService;
import com.ssafy.kkoma.api.member.dto.response.MemberSummaryResponse;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.global.jwt.service.TokenManager;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberInfoController {

    private final TokenManager tokenManager;
    private final MemberInfoService memberInfoService;
    private final MemberService memberService;

    @Tag(name = "Member")
    @Operation(summary = "to get a member info", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/info")
    public ResponseEntity<MemberInfoResponse> getMemberInfo(@MemberInfo MemberInfoDto memberInfoDto) {

        Long memberId = memberInfoDto.getMemberId();
        MemberInfoResponse memberInfoResponse = memberInfoService.getMemberInfo(memberId);

        return ResponseEntity.ok(memberInfoResponse);
    }

    @Tag(name = "Member")
    @Operation(summary = "to get a member summary", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/summary")
    public ResponseEntity<MemberSummaryResponse> getMemberSummary(@MemberInfo MemberInfoDto memberInfoDto) {

        Long memberId = memberInfoDto.getMemberId();
        MemberSummaryResponse memberSummaryResponse = memberInfoService.getMemberSummary(memberId);

        return ResponseEntity.ok(memberSummaryResponse);
    }

    @Tag(name = "Member")
    @Operation(summary = "to update a member", security = { @SecurityRequirement(name = "bearer-key") })
    @PutMapping("/")
    public ResponseEntity<?> updateMember(@MemberInfo MemberInfoDto memberInfoDto, @RequestBody UpdateMemberRequest updateMemberRequest) {

        Long memberId = memberInfoDto.getMemberId();
        MemberInfoResponse memberInfoResponse = memberService.updateMemberInfo(memberId, updateMemberRequest);

        return ResponseEntity.ok(memberInfoResponse);
    }

}
