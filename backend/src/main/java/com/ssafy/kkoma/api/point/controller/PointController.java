package com.ssafy.kkoma.api.point.controller;

import com.ssafy.kkoma.api.common.dto.BasePageResponse;
import com.ssafy.kkoma.api.point.dto.PointHistorySummary;
import com.ssafy.kkoma.api.point.dto.PointSummaryResponse;
import com.ssafy.kkoma.api.point.service.PointHistoryService;
import com.ssafy.kkoma.api.point.service.PointService;
import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.domain.point.entity.PointHistory;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import com.ssafy.kkoma.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Point")
@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;
    private final PointHistoryService pointHistoryService;

    @Tag(name = "Point")
    @Operation(summary = "to get a point summary", security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/summary")
    public ResponseEntity<ApiUtils.ApiResult<PointSummaryResponse>> getPointSummary(
        @MemberInfo MemberInfoDto memberInfoDto
    ) {
        PointSummaryResponse pointSummary = pointService.getPointSummary(memberInfoDto.getMemberId());
        return ResponseEntity.ok(ApiUtils.success(pointSummary));
    }

    @Tag(name = "Point")
    @Operation(summary = "to get a point summary", security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/products/{productId}")
    public ResponseEntity<?> comparePointsToPrice(
        @MemberInfo MemberInfoDto memberInfoDto,
        @PathVariable("productId") Long productId
    ) {
        pointService.comparePointsToPrice(memberInfoDto.getMemberId(), productId);
        return ResponseEntity.ok().build();
    }

    @Tag(name = "Point")
    @Operation(
            summary = "포인트 히스토리 전체 조회",
            description = "[[노션](https://www.notion.so/todays-jiwoo/e2afab87fc3842728c08f00104e01428?pvs=4)] ",
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    @GetMapping("/history")
    public ResponseEntity<?> getPointHistory(
        @MemberInfo MemberInfoDto memberInfoDto,
        Pageable pageable
    ){
        BasePageResponse<PointHistory, PointHistorySummary> pointHistorySummaryListResponse =
                pointHistoryService.getPointHistory(memberInfoDto.getMemberId(), pageable);
        return ResponseEntity.ok(ApiUtils.success(pointHistorySummaryListResponse));
    }
}
