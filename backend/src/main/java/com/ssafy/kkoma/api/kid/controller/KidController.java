package com.ssafy.kkoma.api.kid.controller;

import com.ssafy.kkoma.api.kid.dto.response.KidSummaryResponse;
import com.ssafy.kkoma.api.kid.dto.request.UpdateKidRequest;
import com.ssafy.kkoma.api.kid.service.KidService;
import com.ssafy.kkoma.api.login.dto.OauthLoginDto;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import com.ssafy.kkoma.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Tag(name = "Kid")
    @Operation(
        summary = "전체 아이 요약 정보 조회",
        description = "[[노션](https://www.notion.so/todays-jiwoo/b41eb96ac85e4efab4fba53ee5033255?pvs=4)]",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @GetMapping("/summary")
    public ResponseEntity<ApiUtils.ApiResult<List<KidSummaryResponse>>> getKidSummaryDtos(
            @MemberInfo MemberInfoDto memberInfoDto
    ) {
        Long memberId = memberInfoDto.getMemberId();
        List<KidSummaryResponse> kidSummaryResponseDtos = kidService.getKids(memberId);

        return ResponseEntity
                .ok(ApiUtils.success(kidSummaryResponseDtos));
    }

    @Tag(name = "Kid")
    @Operation(
        summary = "특정 아이 요약 정보 조회",
        description = "[[노션](https://www.notion.so/todays-jiwoo/df0a1ed09fbb4db09246e718a7940051?pvs=4)]",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @GetMapping("/summary/{kidId}")
    public ResponseEntity<ApiUtils.ApiResult<KidSummaryResponse>> getKidSummaryDtos(
        @PathVariable("kidId") Long kidId,
        @MemberInfo MemberInfoDto memberInfoDto
    ) {
        Long memberId = memberInfoDto.getMemberId();
        KidSummaryResponse kidSummaryResponseDto = kidService.getKid(kidId, memberId);

        return ResponseEntity
                .ok(ApiUtils.success(kidSummaryResponseDto));
    }

    @Tag(name = "Kid")
    @Operation(
        summary = "아이 정보 등록",
        description = "[[노션](https://www.notion.so/todays-jiwoo/28b415bd233e4e23a2227f3a94ebac72?pvs=4)]",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @PutMapping
    public ResponseEntity<ApiUtils.ApiResult<KidSummaryResponse>> updateKid(
        @MemberInfo MemberInfoDto memberInfoDto,
        @RequestBody UpdateKidRequest updateKidRequest
    ) {
        Long memberId = memberInfoDto.getMemberId();

        KidSummaryResponse kidSummaryResponseDto = kidService.updateKidInfo(memberId, updateKidRequest);

        return ResponseEntity
                .ok(ApiUtils.success(kidSummaryResponseDto));
    }

    @Tag(name = "Kid")
    @Operation(
        summary = "아이 정보 수정",
        description = "[[노션](https://www.notion.so/todays-jiwoo/c5a20f69c1924ee6baff1dd0db6be4a8?pvs=4)]",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @PutMapping("/{kidId}")
    public ResponseEntity<ApiUtils.ApiResult<KidSummaryResponse>> updateKid(
        @PathVariable("kidId") Long kidId,
        @MemberInfo MemberInfoDto memberInfoDto,
        @RequestBody UpdateKidRequest updateKidRequest
    ) {
        Long memberId = memberInfoDto.getMemberId();
        KidSummaryResponse kidSummaryResponseDto = kidService.updateKidInfo(kidId, memberId, updateKidRequest);

        return ResponseEntity
                .ok(ApiUtils.success(kidSummaryResponseDto));
    }

}
