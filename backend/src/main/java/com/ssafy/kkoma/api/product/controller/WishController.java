package com.ssafy.kkoma.api.product.controller;

import com.ssafy.kkoma.api.common.dto.BasePageResponse;
import com.ssafy.kkoma.api.product.dto.ProductSummary;

import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.api.product.dto.ProductWishResponse;
import com.ssafy.kkoma.domain.product.entity.WishList;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import com.ssafy.kkoma.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class WishController {

    private final ProductService productService;

    @PostMapping("/{productId}/wish")
    public ResponseEntity<ApiUtils.ApiResult<ProductWishResponse>> wishProduct(
        @MemberInfo MemberInfoDto memberInfoDto,
        @PathVariable("productId") Long productId
    ) {
        Long memberId = memberInfoDto.getMemberId();
        ProductWishResponse response = productService.wishProduct(productId, memberId);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @PutMapping("/{productId}/unwish")
    public ResponseEntity<?> unwishProduct(
        @MemberInfo MemberInfoDto memberInfoDto,
        @PathVariable("productId") Long productId
    ) {
        Long memberId = memberInfoDto.getMemberId();
        ProductWishResponse response = productService.unwishProduct(productId, memberId);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @GetMapping("/wishes")
    public ResponseEntity<ApiUtils.ApiResult<BasePageResponse<WishList, ProductSummary>>> getMyWishProducts(
            @MemberInfo MemberInfoDto memberInfoDto,
            Pageable pageable
    ) {
        Long memberId = memberInfoDto.getMemberId();
        BasePageResponse<WishList, ProductSummary> response = productService.getMyWishProducts(memberId, pageable);
        return ResponseEntity.ok(ApiUtils.success(response));
    }
}
