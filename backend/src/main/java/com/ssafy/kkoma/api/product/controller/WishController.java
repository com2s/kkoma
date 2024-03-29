package com.ssafy.kkoma.api.product.controller;

import com.ssafy.kkoma.api.common.dto.BasePageResponse;
import com.ssafy.kkoma.api.product.dto.ProductSummary;

import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.api.product.dto.ProductWishResponse;
import com.ssafy.kkoma.domain.product.entity.WishList;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import com.ssafy.kkoma.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class WishController {

    private final ProductService productService;

    @Tag(name = "Wish")
    @Operation(
            summary = "거래 글 찜하기",
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    @PostMapping("/{productId}/wish")
    public ResponseEntity<ApiUtils.ApiResult<ProductWishResponse>> wishProduct(
        @MemberInfo MemberInfoDto memberInfoDto,
        @PathVariable("productId") Long productId
    ) {
        Long memberId = memberInfoDto.getMemberId();
        ProductWishResponse response = productService.wishProduct(productId, memberId);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @Tag(name = "Wish")
    @Operation(
            summary = "거래 글 찜 취소하기",
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    @PutMapping("/{productId}/unwish")
    public ResponseEntity<?> unwishProduct(
        @MemberInfo MemberInfoDto memberInfoDto,
        @PathVariable("productId") Long productId
    ) {
        Long memberId = memberInfoDto.getMemberId();
        ProductWishResponse response = productService.unwishProduct(productId, memberId);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @Tag(name = "Wish")
    @Operation(
            summary = "내가 찜한 모든 거래 글 조회",
            security = { @SecurityRequirement(name = "bearer-key") }
    )
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
