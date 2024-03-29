package com.ssafy.kkoma.api.point.controller;

import com.ssafy.kkoma.api.point.dto.PointSummaryResponse;
import com.ssafy.kkoma.api.point.service.PointService;
import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import com.ssafy.kkoma.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
}
