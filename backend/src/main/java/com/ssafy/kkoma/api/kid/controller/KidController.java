package com.ssafy.kkoma.api.kid.controller;

import com.ssafy.kkoma.api.kid.dto.KidSummaryResponseDto;
import com.ssafy.kkoma.api.kid.dto.UpdateKidRequestDto;
import com.ssafy.kkoma.api.kid.service.KidService;
import com.ssafy.kkoma.domain.kid.entity.Kid;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.service.MemberService;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kids")
@RequiredArgsConstructor
public class KidController {

    private final KidService kidService;

    @GetMapping("/summary")
    public ResponseEntity<List<KidSummaryResponseDto>> getKidSummaryDtos(@MemberInfo MemberInfoDto memberInfoDto) {

        Long memberId = memberInfoDto.getMemberId();
        List<KidSummaryResponseDto> kidSummaryResponseDtos = kidService.getKids(memberId);

        return ResponseEntity.ok(kidSummaryResponseDtos);
    }

    @GetMapping("/summary/{kidId}")
    public ResponseEntity<KidSummaryResponseDto> getKidSummaryDtos(@PathVariable Long kidId, @MemberInfo MemberInfoDto memberInfoDto) {

        Long memberId = memberInfoDto.getMemberId();
        KidSummaryResponseDto kidSummaryResponseDto = kidService.getKid(kidId, memberId);

        return ResponseEntity.ok(kidSummaryResponseDto);
    }

    @PutMapping
    public ResponseEntity<KidSummaryResponseDto> updateKid(@MemberInfo MemberInfoDto memberInfoDto, @RequestBody UpdateKidRequestDto updateKidRequestDto) {

        Long memberId = memberInfoDto.getMemberId();

        KidSummaryResponseDto kidSummaryResponseDto = kidService.updateKidInfo(memberId, updateKidRequestDto);

        return ResponseEntity.ok(kidSummaryResponseDto);
    }

    @PutMapping("/{kidId}")
    public ResponseEntity<KidSummaryResponseDto> updateKid(@PathVariable Long kidId, @MemberInfo MemberInfoDto memberInfoDto, @RequestBody UpdateKidRequestDto updateKidRequestDto) {

        Long memberId = memberInfoDto.getMemberId();
        KidSummaryResponseDto kidSummaryResponseDto = kidService.updateKidInfo(kidId, memberId, updateKidRequestDto);

        return ResponseEntity.ok(kidSummaryResponseDto);
    }

}
