package com.ssafy.kkoma.api.offer.controller;

import com.ssafy.kkoma.api.offer.dto.OfferTimeRequest;
import com.ssafy.kkoma.api.offer.service.OfferDetailService;
import com.ssafy.kkoma.api.offer.service.OfferService;
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

    @Tag(name = "Offer")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("/products/{productId}")
    public ResponseEntity<?> createOffer(@MemberInfo MemberInfoDto memberInfo, @PathVariable Long productId, @RequestBody List<OfferTimeRequest> offerTimeRequestList){
        Long offerId = offerService.createOffer(memberInfo.getMemberId(), productId);

        offerDetailService.createOfferDetail(offerId, offerTimeRequestList);

        return ResponseEntity.ok().build();
    }

}
