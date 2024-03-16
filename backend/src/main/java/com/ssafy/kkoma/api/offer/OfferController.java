package com.ssafy.kkoma.api.offer;

import com.ssafy.kkoma.domain.offer.dto.request.OfferTimeRequest;
import com.ssafy.kkoma.domain.offer.service.OfferDetailService;
import com.ssafy.kkoma.domain.offer.service.OfferService;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfo;
import com.ssafy.kkoma.global.resolver.memberinfo.MemberInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;
    private final OfferDetailService offerDetailService;

    @PostMapping("/products/{productId}")
    public ResponseEntity<?> createOffer(@MemberInfo MemberInfoDto memberInfo, @PathVariable Long productId, @RequestBody List<OfferTimeRequest> offerTimeRequestList){
        Long offerId = offerService.createOffer(memberInfo.getMemberId(), productId);

        offerDetailService.createOfferDetail(offerId, offerTimeRequestList);

        return ResponseEntity.ok().build();
    }

}
