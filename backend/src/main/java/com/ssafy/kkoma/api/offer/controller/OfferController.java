package com.ssafy.kkoma.api.offer.controller;

import com.ssafy.kkoma.api.deal.service.DealService;
import com.ssafy.kkoma.api.offer.dto.request.OfferTimeRequest;
import com.ssafy.kkoma.api.offer.dto.response.OfferResponse;
import com.ssafy.kkoma.api.offer.service.OfferDetailService;
import com.ssafy.kkoma.api.offer.service.OfferService;
import com.ssafy.kkoma.domain.deal.entity.Deal;
import com.ssafy.kkoma.domain.deal.request.DealTimeRequest;
import com.ssafy.kkoma.domain.offer.entity.Offer;
import com.ssafy.kkoma.domain.product.constant.ProductType;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import io.swagger.v3.oas.annotations.Operation;
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
    private final DealService dealService;

    @Tag(name = "Offer")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/products/{productId}")
    public ResponseEntity<?> createOffer(@MemberInfo MemberInfoDto memberInfo, @PathVariable Long productId, @RequestBody List<OfferTimeRequest> offerTimeRequestList){
        Long offerId = offerService.createOffer(memberInfo.getMemberId(), productId);

        offerDetailService.createOfferDetail(offerId, offerTimeRequestList);

        return ResponseEntity.ok().build();
    }

    @Tag(name = "Offer")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/products/{productId}")
    public ResponseEntity<List<OfferResponse>> getOffers(@PathVariable Long productId){
        List<OfferResponse> offerResponseList = offerService.getOffers(productId);

        return ResponseEntity.ok(offerResponseList);
    }

    @Tag(name = "Offer")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PatchMapping("/{offerId}")
    public ResponseEntity<?> changeOfferStatus(@PathVariable Long offerId, @RequestParam String type, @RequestBody DealTimeRequest dealTimeRequest){
        if(type.equals("accept")){
            Offer offer = offerService.acceptOffer(offerId, dealTimeRequest);
        }

        return ResponseEntity.ok().build();
    }
}
