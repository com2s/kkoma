package com.ssafy.kkoma.api.recommendation.api;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.api.recommendation.service.RecommendationService;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import com.ssafy.kkoma.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/recommend")
    public ResponseEntity<ApiUtils.ApiResult<List<ProductSummary>>> recommendItems(@MemberInfo MemberInfoDto memberInfoDto) throws SQLException {
        List<ProductSummary> recommendedItems = recommendationService.recommendProduct(memberInfoDto.getMemberId());
        return ResponseEntity.ok(ApiUtils.success(recommendedItems));
    }

}
