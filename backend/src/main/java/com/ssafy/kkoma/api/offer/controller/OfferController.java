package com.ssafy.kkoma.api.offer.controller;

import com.ssafy.kkoma.api.deal.dto.request.DecideOfferRequest;
import com.ssafy.kkoma.api.offer.dto.request.OfferTimeRequest;
import com.ssafy.kkoma.api.offer.dto.response.OfferResponse;
import com.ssafy.kkoma.api.offer.dto.response.SendOfferResponse;
import com.ssafy.kkoma.api.offer.dto.response.DecideOfferResponse;
import com.ssafy.kkoma.api.offer.service.OfferDetailService;
import com.ssafy.kkoma.api.offer.service.OfferService;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import com.ssafy.kkoma.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Offer")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;
    private final OfferDetailService offerDetailService;

    @Tag(name = "Offer")
    @Operation(
        summary = "거래 요청",
        description = "[[노션](https://www.notion.so/todays-jiwoo/8ed2ff8952ea47248a565c3b7660ea3b?pvs=4)] 구매 희망자는 거래 희망 시간을 최대 3개 제시하며 구매를 요청한다.",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @PostMapping("/products/{productId}")
    public ResponseEntity<?> createOffer(@MemberInfo MemberInfoDto memberInfo, @PathVariable Long productId, @RequestBody List<OfferTimeRequest> offerTimeRequestList){
        Long offerId = offerService.createOffer(memberInfo.getMemberId(), productId);
        SendOfferResponse sendOfferResponse = offerDetailService.createOfferDetail(offerId, offerTimeRequestList);

        return ResponseEntity.ok(ApiUtils.success(sendOfferResponse));
    }

    @Tag(name = "Offer")
    @Operation(
        summary = "상품글 거래 요청 전체 조회",
        description = "[[노션](https://www.notion.so/todays-jiwoo/85c08cde5bbe47088df9267fefc5c641?pvs=4)] 한 상품글에 전송된 거래 요청 전체 목록을 조회한다.",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @GetMapping("/products/{productId}")
    public ResponseEntity<ApiUtils.ApiResult<List<OfferResponse>>> getOffers(@PathVariable Long productId) {
        List<OfferResponse> offerResponseList = offerService.getOffers(productId);

        return ResponseEntity.ok(ApiUtils.success(offerResponseList));
    }

    @Tag(name = "Offer")
    @Operation(
        summary = "거래 요청 선택",
        description = "[[노션](https://www.notion.so/todays-jiwoo/3aec242d1997458f9cf4337f4134613a?pvs=4)] 판매자는 거래 요청 중 하나를 선택하고 거래 시간을 확정한다.",
        security = { @SecurityRequirement(name = "bearer-key") }
    )
    @PatchMapping("/{offerId}")
    public ResponseEntity<?> decideOffer(
		@PathVariable Long offerId,
        @Parameter(name = "type", description = "거래 수락 시 accept, 거래 거절 시 reject", in = ParameterIn.QUERY) @RequestParam String type,
        @RequestBody DecideOfferRequest decideOfferRequest
    ) {
        DecideOfferResponse decideOfferResponse = null;

        if (type.equals("accept")) {
            decideOfferResponse = offerService.acceptOffer(offerId, decideOfferRequest);
        }

        return ResponseEntity.ok(ApiUtils.success(decideOfferResponse));
    }
}
