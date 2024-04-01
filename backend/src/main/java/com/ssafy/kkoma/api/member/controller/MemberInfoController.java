package com.ssafy.kkoma.api.member.controller;

import com.ssafy.kkoma.api.member.dto.request.UpdateMemberRequest;
import com.ssafy.kkoma.api.member.dto.response.MemberInfoResponse;
import com.ssafy.kkoma.api.member.dto.response.MemberSummaryResponse;
import com.ssafy.kkoma.api.member.dto.response.MyPageMemberProfileResponse;
import com.ssafy.kkoma.api.member.service.MemberInfoService;
import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.global.jwt.service.TokenManager;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import com.ssafy.kkoma.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(
        summary = "유저 상세 정보 조회",
        description = "[[노션](https://www.notion.so/todays-jiwoo/fcde5681f106440db29241109ea65883?pvs=4)]",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @GetMapping("/info")
    public ResponseEntity<ApiUtils.ApiResult<MemberInfoResponse>> getMemberInfo(@MemberInfo MemberInfoDto memberInfoDto) {

        Long memberId = memberInfoDto.getMemberId();
        MemberInfoResponse memberInfoResponse = memberInfoService.getMemberInfo(memberId);

        return ResponseEntity.ok(ApiUtils.success(memberInfoResponse));
    }

    @Tag(name = "Member")
    @Operation(
        summary = "유저 요약 정보 조회",
        description = "[[노션](https://www.notion.so/todays-jiwoo/1d606d654bb74cf0840d2a419b29b8cc?pvs=4)]",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @GetMapping("/summary")
    public ResponseEntity<ApiUtils.ApiResult<MemberSummaryResponse>> getMemberSummary(@MemberInfo MemberInfoDto memberInfoDto) {

        Long memberId = memberInfoDto.getMemberId();
        MemberSummaryResponse memberSummaryResponse = memberInfoService.getMemberSummary(memberId);

        return ResponseEntity.ok(ApiUtils.success(memberSummaryResponse));
    }

    @Tag(name = "Member")
    @Operation(
        summary = "유저 정보 수정",
        description = "[[노션](https://www.notion.so/todays-jiwoo/c35da3d3542847ea895aa2bd7c24066e?pvs=4)]",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @PutMapping
    public ResponseEntity<ApiUtils.ApiResult<MemberInfoResponse>> updateMember(
        @MemberInfo MemberInfoDto memberInfoDto,
        @RequestBody UpdateMemberRequest updateMemberRequest
    ) {
        Long memberId = memberInfoDto.getMemberId();
        MemberInfoResponse memberInfoResponse = memberService.updateMemberInfo(memberId, updateMemberRequest);

        return ResponseEntity.ok(ApiUtils.success(memberInfoResponse));
    }

    @Tag(name = "Member")
    @Operation(
        summary = "마이페이지 프로필 정보 조회",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @GetMapping("/{memberId}/profile")
    public ResponseEntity<ApiUtils.ApiResult<MyPageMemberProfileResponse>> getMyPageMemberProfile(
            @PathVariable Long memberId
    ) {
        MyPageMemberProfileResponse memberProfileResponse = memberInfoService.getMyPageMemberProfile(memberId);

        return ResponseEntity.ok(ApiUtils.success(memberProfileResponse));
    }
}
