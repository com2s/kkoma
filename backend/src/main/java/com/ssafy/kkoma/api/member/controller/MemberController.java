package com.ssafy.kkoma.api.member.controller;

import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.offer.service.OfferService;
import com.ssafy.kkoma.api.product.dto.ProductInfoResponse;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;

import com.ssafy.kkoma.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final OfferService offerService;

    @Tag(name = "member")
    @Operation(summary = "to retrieve my products",
        description = "https://www.notion.so/todays-jiwoo/f-99cdb734385b49eba9972217e5c7b495?pvs=4 여기에 업데이트 해두고 스웨거 고칠게용 (수현)")
    @GetMapping("/products")
    ResponseEntity<ApiUtils.ApiResult<List<ProductInfoResponse>>> getMyProducts(
        @MemberInfo MemberInfoDto memberInfoDto,
        @RequestParam String type
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
            // todo 거래 수락일로 정렬된 결과 반환
            productInfoResponses.addAll(buyingProductResponses);
            productInfoResponses.addAll(sellingProductResponses);
        }

        return ResponseEntity.ok().body(ApiUtils.success(productInfoResponses));
    }

}
