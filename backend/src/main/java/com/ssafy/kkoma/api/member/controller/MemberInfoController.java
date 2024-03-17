package com.ssafy.kkoma.api.member.controller;

import com.ssafy.kkoma.api.member.dto.MemberInfoResponseDto;
import com.ssafy.kkoma.api.member.dto.UpdateMemberRequestDto;
import com.ssafy.kkoma.api.member.service.MemberInfoService;
import com.ssafy.kkoma.domain.member.dto.response.MemberSummaryResponse;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.service.MemberService;
import com.ssafy.kkoma.global.jwt.service.TokenManager;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
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

    @Tag(name = "Member", description = "to get a member info")
    @GetMapping("/info")
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(@MemberInfo MemberInfoDto memberInfoDto) {

        Long memberId = memberInfoDto.getMemberId();
        MemberInfoResponseDto memberInfoResponseDto = memberInfoService.getMemberInfo(memberId);

        return ResponseEntity.ok(memberInfoResponseDto);
    }

    @Tag(name = "Member", description = "to get a member summary")
    @GetMapping("/summary")
    public ResponseEntity<MemberSummaryResponse> getMemberSummary(@MemberInfo MemberInfoDto memberInfoDto) {

        Long memberId = memberInfoDto.getMemberId();
        MemberSummaryResponse memberSummaryResponse = memberInfoService.getMemberSummary(memberId);

        return ResponseEntity.ok(memberSummaryResponse);
    }

    @Tag(name = "Member", description = "to update a member")
    @PutMapping("/")
    public ResponseEntity<?> updateMember(@MemberInfo MemberInfoDto memberInfoDto, @RequestBody UpdateMemberRequestDto updateMemberRequestDto) {

        Long memberId = memberInfoDto.getMemberId();
        MemberInfoResponseDto memberInfoResponseDto = memberService.updateMemberInfo(memberId, updateMemberRequestDto);

        return ResponseEntity.ok(memberInfoResponseDto);
    }

}
