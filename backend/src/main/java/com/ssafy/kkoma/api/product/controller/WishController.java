package com.ssafy.kkoma.api.product.controller;

import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.api.product.dto.ProductWishResponse;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import com.ssafy.kkoma.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class WishController {

    private final ProductService productService;

    @PostMapping("/{productId}/wish")
    public ResponseEntity<ApiUtils.ApiResult<ProductWishResponse>> wishProduct(@MemberInfo MemberInfoDto memberInfoDto, @PathVariable Long productId) {
        Long memberId = memberInfoDto.getMemberId();
        ProductWishResponse response = productService.wishProduct(productId, memberId);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @PutMapping("/{productId}/unwish")
    public ResponseEntity<?> unwishProduct(@MemberInfo MemberInfoDto memberInfoDto, @PathVariable Long productId) {
        Long memberId = memberInfoDto.getMemberId();
        ProductWishResponse response = productService.unwishProduct(productId, memberId);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

}
