package com.ssafy.kkoma.api.kid.controller;

import com.ssafy.kkoma.api.kid.dto.KidSummaryResponseDto;
import com.ssafy.kkoma.api.kid.dto.UpdateKidRequestDto;
import com.ssafy.kkoma.api.kid.service.KidService;
import com.ssafy.kkoma.domain.kid.entity.Kid;
import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.member.service.MemberService;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Kid")
@RestController
@RequestMapping("/api/kids")
@RequiredArgsConstructor
public class KidController {

    private final KidService kidService;

    @Tag(name = "Kid", description = "to get kid summary list")
    @GetMapping("/summary")
    public ResponseEntity<List<KidSummaryResponseDto>> getKidSummaryDtos(@MemberInfo MemberInfoDto memberInfoDto) {

        Long memberId = memberInfoDto.getMemberId();
        List<KidSummaryResponseDto> kidSummaryResponseDtos = kidService.getKids(memberId);

        return ResponseEntity.ok(kidSummaryResponseDtos);
    }

    @Tag(name = "Kid", description = "to get a kid summary")
    @GetMapping("/summary/{kidId}")
    public ResponseEntity<KidSummaryResponseDto> getKidSummaryDtos(@PathVariable Long kidId, @MemberInfo MemberInfoDto memberInfoDto) {

        Long memberId = memberInfoDto.getMemberId();
        KidSummaryResponseDto kidSummaryResponseDto = kidService.getKid(kidId, memberId);

        return ResponseEntity.ok(kidSummaryResponseDto);
    }

    @Tag(name = "Kid", description = "to update a kid")
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
