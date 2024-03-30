package com.ssafy.kkoma.api.product.controller;

import com.ssafy.kkoma.api.common.dto.BasePageResponse;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.api.product.service.ViewHistoryService;
import com.ssafy.kkoma.domain.product.entity.ViewHistory;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import com.ssafy.kkoma.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ViewHistoryController {

    private final ViewHistoryService viewHistoryService;

    @GetMapping("/viewHistories")
    public ResponseEntity<ApiUtils.ApiResult<BasePageResponse<ViewHistory, ProductSummary>>> getMyViewHistories(@MemberInfo MemberInfoDto memberInfoDto, Pageable pageable) {
        BasePageResponse<ViewHistory, ProductSummary> myViewHistoriesResponse = viewHistoryService.getMyViewHistories(memberInfoDto.getMemberId(), pageable);
        return ResponseEntity.ok(ApiUtils.success(myViewHistoriesResponse));
    }

}
