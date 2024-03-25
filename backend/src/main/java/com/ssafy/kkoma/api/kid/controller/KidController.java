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
    @Operation(summary = "to get kid summary list", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/summary")
    public ResponseEntity<ApiUtils.ApiResult<List<KidSummaryResponse>>> getKidSummaryDtos(@MemberInfo MemberInfoDto memberInfoDto) {

        Long memberId = memberInfoDto.getMemberId();
        List<KidSummaryResponse> kidSummaryResponseDtos = kidService.getKids(memberId);

        return ResponseEntity
                .ok(ApiUtils.success(kidSummaryResponseDtos));
    }

    @Tag(name = "Kid")
    @Operation(summary = "to get a kid summary", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/summary/{kidId}")
    public ResponseEntity<ApiUtils.ApiResult<KidSummaryResponse>> getKidSummaryDtos(@PathVariable Long kidId, @MemberInfo MemberInfoDto memberInfoDto) {

        Long memberId = memberInfoDto.getMemberId();
        KidSummaryResponse kidSummaryResponseDto = kidService.getKid(kidId, memberId);

        return ResponseEntity
                .ok(ApiUtils.success(kidSummaryResponseDto));
    }

    @Tag(name = "Kid")
    @Operation(summary = "to update a kid", security = { @SecurityRequirement(name = "bearer-key") })
    @PutMapping
    public ResponseEntity<ApiUtils.ApiResult<KidSummaryResponse>> updateKid(@MemberInfo MemberInfoDto memberInfoDto, @RequestBody UpdateKidRequest updateKidRequest) {

        Long memberId = memberInfoDto.getMemberId();

        KidSummaryResponse kidSummaryResponseDto = kidService.updateKidInfo(memberId, updateKidRequest);

        return ResponseEntity
                .ok(ApiUtils.success(kidSummaryResponseDto));
    }

    @Tag(name = "Kid")
    @Operation(summary = "to update a kid", security = { @SecurityRequirement(name = "bearer-key") })
    @PutMapping("/{kidId}")
    public ResponseEntity<ApiUtils.ApiResult<KidSummaryResponse>> updateKid(@PathVariable Long kidId, @MemberInfo MemberInfoDto memberInfoDto, @RequestBody UpdateKidRequest updateKidRequest) {

        Long memberId = memberInfoDto.getMemberId();
        KidSummaryResponse kidSummaryResponseDto = kidService.updateKidInfo(kidId, memberId, updateKidRequest);

        return ResponseEntity
                .ok(ApiUtils.success(kidSummaryResponseDto));
    }

}
