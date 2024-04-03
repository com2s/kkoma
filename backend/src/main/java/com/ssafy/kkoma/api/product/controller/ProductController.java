package com.ssafy.kkoma.api.product.controller;

import com.ssafy.kkoma.api.product.dto.ProductCreateRequest;
import com.ssafy.kkoma.api.product.dto.ProductDetailResponse;
import com.ssafy.kkoma.api.product.dto.ProductSummary;
import com.ssafy.kkoma.api.product.dto.hourly.ProductHourlyViewedResponse;
import com.ssafy.kkoma.api.product.dto.hourly.ProductHourlyWishedResponse;
import com.ssafy.kkoma.api.product.dto.request.SearchProductRequest;
import com.ssafy.kkoma.api.product.dto.response.SearchProductResponse;
import com.ssafy.kkoma.api.product.service.CategoryPreferenceService;
import com.ssafy.kkoma.api.product.service.ProductService;
import com.ssafy.kkoma.api.product.service.ViewHistoryService;
import com.ssafy.kkoma.api.redis.constant.RedisKeyName;
import com.ssafy.kkoma.api.redis.service.RedisService;
import com.ssafy.kkoma.domain.product.constant.CategoryPreferenceType;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import com.ssafy.kkoma.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Product")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryPreferenceService categoryPreferenceService;
    private final ViewHistoryService viewHistoryService;
    private final RedisService redisService;

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
        summary = "상품글 요약 정보 검색",
        description = "[[노션](https://www.notion.so/todays-jiwoo/09aca23d446748afb8fddff63f771ee7?pvs=4)] ",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @GetMapping("/search")
    public ResponseEntity<ApiUtils.ApiResult<SearchProductResponse>> searchProducts(
            @MemberInfo MemberInfoDto memberInfoDto,
            SearchProductRequest searchProductRequest,
            Pageable pageable
    ) {
        SearchProductResponse searchProductResponse = productService.searchProduct(memberInfoDto.getMemberId(), searchProductRequest, pageable);
        return ResponseEntity.ok(ApiUtils.success(searchProductResponse));
    }

    @Tag(name = "Product")
    @Operation(
        summary = "상품글 상세 정보 조회",
        description = "[[노션](https://www.notion.so/todays-jiwoo/2e2aed347d6d4f93ab72c8ed089e9447?pvs=4)] ",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @GetMapping("/{productId}")
    public ResponseEntity<ApiUtils.ApiResult<ProductDetailResponse>> getProduct(
            @MemberInfo MemberInfoDto memberInfoDto,
            @PathVariable("productId") Long productId
    ) {
        ProductDetailResponse productDetailResponse = productService.getProduct(productId, memberInfoDto.getMemberId());
        productService.addViewCount(productId);

        viewHistoryService.createViewHistory(memberInfoDto.getMemberId(), productId);

        // todo-siyoon 서비스 레이어로 옮기기
        Product product = productService.findProductByProductId(productId);
        Integer categoryId = product.getCategory().getId();
        // ----------

        categoryPreferenceService.renewCategoryPreference(memberInfoDto.getMemberId(), categoryId, CategoryPreferenceType.VIEW);
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

    @Tag(name = "Product")
    @Operation(
        summary = "상품 검색어 자동완성 키워드 조회",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @GetMapping("/search/complete")
    public ResponseEntity<ApiUtils.ApiResult<List<String>>> getAutoComplete(
        @RequestParam("keyword"
    ) String keyword) {
        List<String> response = productService.getAutoCompleteKeyword(keyword);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @Tag(name = "Product")
    @Operation(
        summary = "시간별 찜수 상위 4개 상품글 조회",
            description = "[[노션](https://www.notion.so/todays-jiwoo/4-489f86dd7e1a4c9488d1ede5020e0b8c?pvs=4)]",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @GetMapping("/hourly/wish")
    public ResponseEntity<ApiUtils.ApiResult<?>> getHourlyMostWishedProducts() throws IOException {
        String data = redisService.findHourlyData(RedisKeyName.hourlyWishedProductList, LocalDateTime.now());

        ObjectMapper mapper = new ObjectMapper();
        ArrayList list = mapper.readValue(data, new ArrayList<ProductHourlyWishedResponse>().getClass());

        return ResponseEntity.ok(ApiUtils.success(list));
    }

    @Tag(name = "Product")
    @Operation(
        summary = "시간별 조회수 상위 4개 상품글 조회",
        description = "[[노션](https://www.notion.so/todays-jiwoo/4-d5f5d55c312c4380b711f7ba10b06d66?pvs=4)]",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @GetMapping("/hourly/view")
    public ResponseEntity<ApiUtils.ApiResult<?>> getHourlyMostViewedProducts() throws IOException {
        String data = redisService.findHourlyData(RedisKeyName.hourlyViewedProductList, LocalDateTime.now());

        ObjectMapper mapper = new ObjectMapper();
        ArrayList list = mapper.readValue(data, new ArrayList<ProductHourlyViewedResponse>().getClass());

        return ResponseEntity.ok(ApiUtils.success(list));
    }

}
