package com.ssafy.kkoma.api.member.controller;

import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.offer.service.OfferService;
import com.ssafy.kkoma.api.product.dto.ProductInfoResponse;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;

import com.ssafy.kkoma.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final OfferService offerService;

    @Tag(name = "Member Activity")
    @Operation(
        summary = "유저 관련 상품글 전체 조회",
        description = "[[노션](https://www.notion.so/todays-jiwoo/5aa3a0c8457942fc87def3e075a8e7a1?pvs=4)]",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @GetMapping("/products")
    ResponseEntity<ApiUtils.ApiResult<List<ProductInfoResponse>>> getMyProducts(
        @MemberInfo MemberInfoDto memberInfoDto,
        @RequestParam("type") String type
    ) {
        Long memberId = memberInfoDto.getMemberId();
        List<ProductInfoResponse> productInfoResponses = new ArrayList<>();

        if ("sell".equals(type)) {
            productInfoResponses = memberService.getMySellingProducts(memberId, ProductType.SALE, ProductType.SOLD);
        } else if ("buy".equals(type)) {
            productInfoResponses = offerService.getNotProgressOfferingProducts(memberId);
        } else if ("progress".equals(type)) {
            List<ProductInfoResponse> buyingProductResponses = offerService.getProgressOfferingProducts(memberId);
            List<ProductInfoResponse> sellingProductResponses = memberService.getMySellingProducts(memberId, ProductType.PROGRESS);
            productInfoResponses.addAll(buyingProductResponses);
            productInfoResponses.addAll(sellingProductResponses);
            productInfoResponses.sort(Comparator.comparing(ProductInfoResponse::getSelectedTime).reversed()); // 거래 수락일로 정렬
        }

        return ResponseEntity.ok().body(ApiUtils.success(productInfoResponses));
    }

}
