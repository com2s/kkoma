package com.ssafy.kkoma.api.member.controller;

import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.offer.service.OfferService;
import com.ssafy.kkoma.api.product.dto.ProductInfoResponse;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;

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
        description = "type=sell : 내가 판매자로 참여한 모든 거래 글\n"
            + "type=buy :내가 구매요청한 모든 거래 글 중에서 현재 거래 진행중인 거래 글만 제외하고 조회\n"
            + "type=progress : 내가 구매자 혹은 판매자로 참여한 거래 글 중에서 현재 진행중인 글만 조회\n")
    @GetMapping("/products")
    ResponseEntity<List<ProductInfoResponse>> getMySellingProducts(@MemberInfo MemberInfoDto memberInfoDto, @RequestParam String type) {
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

        return ResponseEntity.ok().body(productInfoResponses);
    }

}
