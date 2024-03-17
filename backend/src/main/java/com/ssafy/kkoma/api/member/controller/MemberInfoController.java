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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberInfoController {

    private final TokenManager tokenManager;
    private final MemberInfoService memberInfoService;
    private final MemberService memberService;

    @GetMapping("/info")
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(@MemberInfo MemberInfoDto memberInfoDto) {

        Long memberId = memberInfoDto.getMemberId();
        MemberInfoResponseDto memberInfoResponseDto = memberInfoService.getMemberInfo(memberId);

        return ResponseEntity.ok(memberInfoResponseDto);
    }

    @GetMapping("/summary")
    public ResponseEntity<MemberSummaryResponse> getMemberSummary(@MemberInfo MemberInfoDto memberInfoDto) {

        Long memberId = memberInfoDto.getMemberId();
        MemberSummaryResponse memberSummaryResponse = memberInfoService.getMemberSummary(memberId);

        return ResponseEntity.ok(memberSummaryResponse);
    }


    @PutMapping("/")
    public ResponseEntity<?> updateMember(@MemberInfo MemberInfoDto memberInfoDto, @RequestBody UpdateMemberRequestDto updateMemberRequestDto) {

        Long memberId = memberInfoDto.getMemberId();
        MemberInfoResponseDto memberInfoResponseDto = memberService.updateMemberInfo(memberId, updateMemberRequestDto);

        return ResponseEntity.ok(memberInfoResponseDto);
    }

}
