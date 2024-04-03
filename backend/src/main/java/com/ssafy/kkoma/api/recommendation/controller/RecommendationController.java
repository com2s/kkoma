package com.ssafy.kkoma.api.recommendation.controller;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.api.recommendation.service.RecommendationService;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import com.ssafy.kkoma.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@Tag(name = "Recommendation")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Tag(name = "Recommendation")
    @GetMapping("/recommend")
    public ResponseEntity<ApiUtils.ApiResult<List<ProductSummary>>> recommendItems(@MemberInfo MemberInfoDto memberInfoDto, @RequestParam Integer num) throws SQLException, TasteException {
        Long memberId = memberInfoDto.getMemberId();
        List<ProductSummary> recommendedItems = recommendationService.recommendProduct(memberId, num);
        return ResponseEntity.ok(ApiUtils.success(recommendedItems));
    }

}
