package com.ssafy.kkoma.api.point.api;

import com.ssafy.kkoma.api.point.dto.PointSummaryResponseDto;
import com.ssafy.kkoma.api.point.service.PointService;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Point")
@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @Tag(name = "Point", description = "to get a point summary")
    @GetMapping("/summary")
    public ResponseEntity<PointSummaryResponseDto> getPointSummary(@MemberInfo MemberInfoDto memberInfoDto) {
        PointSummaryResponseDto pointSummary = pointService.getPointSummary(memberInfoDto.getMemberId());
        return ResponseEntity.ok().body(pointSummary);
    }

}
