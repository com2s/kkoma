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
    @Operation(summary = "to get product summary LIST", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("")
    public ResponseEntity<ApiUtils.ApiResult<List<ProductSummary>>> getProducts(){
        List<ProductSummary> products = productService.getProducts();
        return ResponseEntity.ok(ApiUtils.success(products));
    }

    @Tag(name = "Product")
    @Operation(summary = "to get ONE product detail", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/{productId}")
    public ResponseEntity<ApiUtils.ApiResult<ProductDetailResponse>> getProduct(@PathVariable Long productId){
        ProductDetailResponse productDetailResponse = productService.getProduct(productId);
        productService.addViewCount(productId);
        return ResponseEntity.ok(ApiUtils.success(productDetailResponse));
    }

    @Tag(name = "Product")
    @Operation(summary = "to post a product", security = { @SecurityRequirement(name = "bearer-key") })
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
