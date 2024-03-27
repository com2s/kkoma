package com.ssafy.kkoma.api.product.controller;

import com.ssafy.kkoma.api.product.dto.ProductCreateRequest;
import com.ssafy.kkoma.api.product.dto.ProductCreateResponse;
import com.ssafy.kkoma.api.product.dto.ProductDetailResponse;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;

import com.ssafy.kkoma.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Tag(name = "Product")
    @Operation(
        summary = "상품글 요약 정보 전체 조회",
        description = "[[노션](https://www.notion.so/todays-jiwoo/ac88e2e45bbd4586aeb8997101fc3a7e?pvs=4)] ",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @GetMapping("")
    public ResponseEntity<ApiUtils.ApiResult<List<ProductSummary>>> getProducts(){
        List<ProductSummary> products = productService.getProducts();
        return ResponseEntity.ok(ApiUtils.success(products));
    }

    @Tag(name = "Product")
    @Operation(
        summary = "상품글 상세 정보 조회",
        description = "[[노션](https://www.notion.so/todays-jiwoo/2e2aed347d6d4f93ab72c8ed089e9447?pvs=4)] ",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @GetMapping("/{productId}")
    public ResponseEntity<ApiUtils.ApiResult<ProductDetailResponse>> getProduct(@PathVariable Long productId){
        ProductDetailResponse productDetailResponse = productService.getProduct(productId);
        return ResponseEntity.ok(ApiUtils.success(productDetailResponse));
    }

    @Tag(name = "Product")
    @Operation(
        summary = "상품글 등록",
        description = "[[노션](https://www.notion.so/todays-jiwoo/fe1d7b4fb45d4c1e89d43027d9f4d60f?pvs=4)] 판매자는 판매할 상품 글을 작성한다.",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @PostMapping
    public ResponseEntity<ApiUtils.ApiResult<ProductDetailResponse>> createProduct(
        @MemberInfo MemberInfoDto memberInfoDto,
        @RequestBody ProductCreateRequest productCreateRequest
    ) {
        ProductDetailResponse productDetailResponse
            = productService.createProduct(memberInfoDto.getMemberId(), productCreateRequest);
        return ResponseEntity.ok().body(ApiUtils.success(productDetailResponse));
    }

}
