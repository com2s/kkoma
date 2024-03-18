package com.ssafy.kkoma.api.kid.controller;

import com.ssafy.kkoma.api.kid.dto.response.KidSummaryResponse;
import com.ssafy.kkoma.api.kid.dto.request.UpdateKidRequest;
import com.ssafy.kkoma.api.kid.service.KidService;
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
    public ResponseEntity<List<KidSummaryResponse>> getKidSummaryDtos(@MemberInfo MemberInfoDto memberInfoDto) {

        Long memberId = memberInfoDto.getMemberId();
        List<KidSummaryResponse> kidSummaryResponseDtos = kidService.getKids(memberId);

        return ResponseEntity.ok(kidSummaryResponseDtos);
    }

    @Tag(name = "Kid", description = "to get a kid summary")
    @GetMapping("/summary/{kidId}")
    public ResponseEntity<KidSummaryResponse> getKidSummaryDtos(@PathVariable Long kidId, @MemberInfo MemberInfoDto memberInfoDto) {

        Long memberId = memberInfoDto.getMemberId();
        KidSummaryResponse kidSummaryResponseDto = kidService.getKid(kidId, memberId);

        return ResponseEntity.ok(kidSummaryResponseDto);
    }

    @Tag(name = "Kid", description = "to update a kid")
    @PutMapping
    public ResponseEntity<KidSummaryResponse> updateKid(@MemberInfo MemberInfoDto memberInfoDto, @RequestBody UpdateKidRequest updateKidRequest) {

        Long memberId = memberInfoDto.getMemberId();

        KidSummaryResponse kidSummaryResponseDto = kidService.updateKidInfo(memberId, updateKidRequest);

        return ResponseEntity.ok(kidSummaryResponseDto);
    }

    @PutMapping("/{kidId}")
    public ResponseEntity<KidSummaryResponse> updateKid(@PathVariable Long kidId, @MemberInfo MemberInfoDto memberInfoDto, @RequestBody UpdateKidRequest updateKidRequest) {

        Long memberId = memberInfoDto.getMemberId();
        KidSummaryResponse kidSummaryResponseDto = kidService.updateKidInfo(kidId, memberId, updateKidRequest);

        return ResponseEntity.ok(kidSummaryResponseDto);
    }

}
