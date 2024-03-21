package com.ssafy.kkoma.api.member.controller;

import com.ssafy.kkoma.api.member.service.MemberService;
import com.ssafy.kkoma.api.offer.service.OfferService;
import com.ssafy.kkoma.api.product.dto.ProductInfoResponse;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final OfferService offerService;

    @GetMapping("/products")
    ResponseEntity<List<ProductInfoResponse>> getMySellingProducts(@MemberInfo MemberInfoDto memberInfoDto) {
        Long memberId = memberInfoDto.getMemberId();
        List<ProductInfoResponse> productInfoResponses = memberService.getMySellingProducts(memberId, ProductType.SALE, ProductType.SOLD);
        return ResponseEntity.ok().body(productInfoResponses);
    }

    @GetMapping("/not-progress-offering-products")
    ResponseEntity<List<ProductInfoResponse>> getMyNotProgressOfferingProducts(@MemberInfo MemberInfoDto memberInfoDto) {
        Long memberId = memberInfoDto.getMemberId();
        List<ProductInfoResponse> productInfoResponses = offerService.getNotProgressOfferingProducts(memberId);
        return ResponseEntity.ok().body(productInfoResponses);
    }

    @GetMapping("/progress-offering-products")
    ResponseEntity<List<ProductInfoResponse>> getMyProgressOfferingProducts(@MemberInfo MemberInfoDto memberInfoDto) {
        Long memberId = memberInfoDto.getMemberId();
        List<ProductInfoResponse> buyingProductResponses = offerService.getProgressOfferingProducts(memberId);
        List<ProductInfoResponse> sellingProductResponses = memberService.getMySellingProducts(memberId, ProductType.PROGRESS);
        buyingProductResponses.addAll(sellingProductResponses);
        // todo 거래 수락일로 정렬된 결과 반환

        return ResponseEntity.ok().body(buyingProductResponses);
    }

}
